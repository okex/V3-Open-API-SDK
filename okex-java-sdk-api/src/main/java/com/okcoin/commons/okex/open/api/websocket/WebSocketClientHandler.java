package com.okcoin.commons.okex.open.api.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import java.util.zip.Inflater;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private ChannelPromise handshakeFuture;
    private final WebSocketClientHandshaker handshaker;
    private WebSocketClient webSocketClient;
    private WebSocketListener listener;

    public WebSocketClientHandler(WebSocketClient webSocketClient, WebSocketClientHandshaker handshaker, WebSocketListener listener) {
        this.handshaker = handshaker;
        this.webSocketClient = webSocketClient;
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            this.listener.onWebsocketOpen(this.webSocketClient);
            this.webSocketClient.beginTimer();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof PongWebSocketFrame){
            this.listener.onWebsocketPong(webSocketClient);
        }
        else if (frame instanceof CloseWebSocketFrame) {
            CloseWebSocketFrame closeFrame = (CloseWebSocketFrame)frame;
            int closeCode = closeFrame.statusCode();
            this.listener.onWebsocketClose(webSocketClient, closeCode);
        }
        else if (frame instanceof BinaryWebSocketFrame) {
            //这里处理收到的逻辑
            String msgStr = decode(msg);
            if (msgStr.equals("pong")) {
                this.listener.onWebsocketPong(webSocketClient);
            } else {
                this.listener.onTextMessage(webSocketClient, msgStr);
            }
        }
        else if (frame instanceof TextWebSocketFrame) {
            String txt = ((TextWebSocketFrame) frame).text();
            if (txt.equals("pong")) {
                this.listener.onWebsocketPong(webSocketClient);
            } else {
                this.listener.onTextMessage(webSocketClient, txt);
            }
        }
        else {
            this.listener.handleCallbackError(webSocketClient, new RuntimeException("cannot decode message"));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        listener.handleCallbackError(webSocketClient,cause);

        //do more exception handling
        ctx.close();
    }

    private static String uncompress(ByteBuf buf) {
        try {
            byte[] temp = new byte[buf.readableBytes()];
            ByteBufInputStream bis = new ByteBufInputStream(buf);
            bis.read(temp);
            bis.close();
            Inflater decompresser = new Inflater(true);
            decompresser.setInput(temp, 0, temp.length);
            StringBuilder sb = new StringBuilder();
            byte[] result = new byte[1024];
            while (!decompresser.finished()) {
                int resultLength = decompresser.inflate(result);
                sb.append(new String(result, 0, resultLength, "UTF-8"));
            }
            decompresser.end();
            return sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String decode(Object msg){
        BinaryWebSocketFrame frameBinary = (BinaryWebSocketFrame)msg;
        byte[] bytes = new byte[frameBinary.content().readableBytes()];
        frameBinary.content().readBytes(bytes);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        String str = uncompress(byteBuf);
        return str;
    }
}
