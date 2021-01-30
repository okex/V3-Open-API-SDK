package com.okcoin.commons.okex.open.api.client;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.constant.APIConstants;
import com.okcoin.commons.okex.open.api.enums.ContentTypeEnum;
import com.okcoin.commons.okex.open.api.enums.HttpHeadersEnum;
import com.okcoin.commons.okex.open.api.exception.APIException;
import com.okcoin.commons.okex.open.api.utils.DateUtils;
import com.okcoin.commons.okex.open.api.utils.HmacSHA256Base64Utils;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * API OkHttpClient.
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/8 14:14
 */
public class APIHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(APIHttpClient.class);

    private final APIConfiguration config;
    private final APICredentials credentials;


    public APIHttpClient(final APIConfiguration config, final APICredentials credentials) {
        this.config = config;
        this.credentials = credentials;
    }

    /**
     * Get a ok http 3 client object. <br/>
     * Declare:
     * <blockquote><pre>
     *  1. Set default client args:
     *         connectTimeout=30s
     *         readTimeout=30s
     *         writeTimeout=30s
     *         retryOnConnectionFailure=true.
     *  2. Set request headers:
     *      Content-Type: application/json; charset=UTF-8  (default)
     *      Cookie: locale=en_US        (English)
     *      OK-ACCESS-KEY: (Your setting)
     *      OK-ACCESS-SIGN: (Use your setting, auto sign and add)
     *      OK-ACCESS-TIMESTAMP: (Auto add)
     *      OK-ACCESS-PASSPHRASE: Your setting
     *  3. Set default print api info: false.
     * </pre></blockquote>
     */
    public OkHttpClient client() {
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(this.config.getConnectTimeout(), TimeUnit.SECONDS);
        clientBuilder.readTimeout(this.config.getReadTimeout(), TimeUnit.SECONDS);
        clientBuilder.writeTimeout(this.config.getWriteTimeout(), TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(this.config.isRetryOnConnectionFailure());
        clientBuilder.addInterceptor((Interceptor.Chain chain) -> {
            final Request.Builder requestBuilder = chain.request().newBuilder();
            final String timestamp = DateUtils.getUnixTime();
            //打印首行时间戳
//            System.out.println("时间戳timestamp={" + timestamp + "}");
//              设置模拟盘请求头
//            String simulated = "1";
            requestBuilder.headers(this.headers(chain.request(), timestamp));
            final Request request = requestBuilder.build();
            if (this.config.isPrint()) {
                this.printRequest(request, timestamp);
            }
            return chain.proceed(request);
        });
        return clientBuilder.build();
    }

//    ,String simulated
    private Headers headers(final Request request, final String timestamp) {
        final Headers.Builder builder = new Headers.Builder();
        builder.add(APIConstants.ACCEPT, ContentTypeEnum.APPLICATION_JSON.contentType());
        builder.add(APIConstants.CONTENT_TYPE, ContentTypeEnum.APPLICATION_JSON_UTF8.contentType());
        builder.add(APIConstants.COOKIE, this.getCookie());

        if (StringUtils.isNotEmpty(this.credentials.getSecretKey())) {
            //拼接上秘钥，密码，签名和时间戳
            builder.add(HttpHeadersEnum.OK_ACCESS_KEY.header(), this.credentials.getApiKey());
            builder.add(HttpHeadersEnum.OK_ACCESS_SIGN.header(), this.sign(request, timestamp));
            builder.add(HttpHeadersEnum.OK_ACCESS_TIMESTAMP.header(), timestamp);
            builder.add(HttpHeadersEnum.OK_ACCESS_PASSPHRASE.header(), this.credentials.getPassphrase());
//            builder.add("x-simulated-trading","1");
//            System.out.println("__________simulated:"+simulated);
//            builder.add(HttpHeadersEnum.x_simulated_trading.header(),simulated);
        }

        return builder.build();
    }

    private String getCookie() {
        final StringBuilder cookie = new StringBuilder();
        cookie.append(APIConstants.LOCALE).append(this.config.getI18n().i18n());
        return cookie.toString();
    }

    private String sign(final Request request, final String timestamp) {
        final String sign;

        try {
            sign = HmacSHA256Base64Utils.sign(timestamp, this.method(request), this.requestPath(request),
                    this.queryString(request), this.body(request), this.credentials.getSecretKey());
            //System.out.println("签名字符串："+timestamp+this.method(request)+this.requestPath(request)+this.queryString(request)+this.body(request));
        } catch (final IOException e) {
            throw new APIException("Request get body io exception.", e);
        } catch (final CloneNotSupportedException e) {
            throw new APIException("Hmac SHA256 Base64 Signature clone not supported exception.", e);
        } catch (final InvalidKeyException e) {
            throw new APIException("Hmac SHA256 Base64 Signature invalid key exception.", e);
        }
        return sign;
    }
    //返回请求路径url
    private String url(final Request request) {
        return request.url().toString();
    }
    //将请求方法转变为大写，并返回
    private String method(final Request request) {
        return request.method().toUpperCase();
    }
    //返回请求路径
    private String requestPath(final Request request) {
        String url = this.url(request);
        url = url.replace(this.config.getEndpoint(), APIConstants.EMPTY);
        String requestPath = url;
        if (requestPath.contains(APIConstants.QUESTION)) {
            requestPath = requestPath.substring(0, url.lastIndexOf(APIConstants.QUESTION));
        }
        if(this.config.getEndpoint().endsWith(APIConstants.SLASH)){
            requestPath = APIConstants.SLASH + requestPath;
        }
        return requestPath;
    }

    private String queryString(final Request request) {
        final String url = this.url(request);
        request.body();
        //请求参数为空字符串
        String queryString = APIConstants.EMPTY;
        //如果URL中包含？即存在参数的拼接
        if (url.contains(APIConstants.QUESTION)) {
            queryString = url.substring(url.lastIndexOf(APIConstants.QUESTION) + 1);
        }
        return queryString;
    }

    private String body(final Request request) throws IOException {
        final RequestBody requestBody = request.body();
        String body = APIConstants.EMPTY;
        if (requestBody != null) {
            final Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            body = buffer.readString(APIConstants.UTF_8);
        }
        return body;
    }

    private void printRequest(final Request request, final String timestamp) {
        final String method = this.method(request);
        final String requestPath = this.requestPath(request);

        final String queryString = this.queryString(request);

        final String body;
        try {
            body = this.body(request);
        } catch (final IOException e) {
            throw new APIException("Request get body io exception.", e);
        }
        final StringBuilder requestInfo = new StringBuilder();


        requestInfo.append("\n\tRequest").append("(").append(DateUtils.timeToString(null, 4)).append("):");
       //拼接Url
        requestInfo.append("\n\t\t").append("Url: ").append(this.url(request));
        requestInfo.append("\n\t\t").append("Method: ").append(method);
        requestInfo.append("\n\t\t").append("Headers: ");
        final Headers headers = request.headers();
        if (headers != null && headers.size() > 0) {
            for (final String name : headers.names()) {
                requestInfo.append("\n\t\t\t").append(name).append(": ").append(headers.get(name));
            }
        }
        requestInfo.append("\n\t\t").append("request body: ").append(body);
        final String preHash = HmacSHA256Base64Utils.preHash(timestamp, method, requestPath, queryString, body);
        requestInfo.append("\n\t\t").append("preHash: ").append(preHash);
        APIHttpClient.LOG.info(requestInfo.toString());
    }
}
