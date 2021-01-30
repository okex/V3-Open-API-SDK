OKCoin OKEX V3 Open Api使用说明
--------------
### 1.使用技术：okhttp3 + retrofit2

```
### 2.简单使用方式:
```
 public static void main(String[] args) {

        APIConfiguration config = new APIConfiguration();
        config.setEndpoint("https://www.okex.com/");
        //secretKey,api注册成功后页面上有
        config.setApiKey("");
        config.setSecretKey("");
        //Passphrase忘记后无法找回
        config.setPassphrase("");
        config.setPrint(true);

        GeneralAPIService marketAPIService = new GeneralAPIServiceImpl(config);
        ServerTime time = marketAPIService.getServerTime();
        System.out.println(JSON.toJSONString(time));

        FuturesTradeAPIService tradeAPIService = new FuturesTradeAPIServiceImpl(config);

        Order order = new Order();
        order.setClient_oid("OkexTestFuturesOrder2020");
        order.setInstrument_id("BTC-USD-200626");
        order.setType("1");
        order.setPrice("7000");
        order.setSize("400");
        order.setMatch_price("0");
        order.setOrder_type("0");
        OrderResult orderResult = tradeAPIService.newOrder(order); 
        System.out.println(JSON.toJSONString(orderResult));
 }
```
### 3.Spring 或 Spring Boot使用方式:
```
@RestController
public class TestOKEXOpenApiV3 {

    @Autowired
    private GeneralAPIService generalAPIService;

    @GetMapping("/server-time")
    public ServerTime getServerTime() {
        return generalAPIService.getServerTime();
    }
    
    @Bean
    public APIConfiguration okexApiConfig() {
        APIConfiguration config = new APIConfiguration();
          config.setEndpoint(" https://www.okex.com/");
            //secretKey,api注册成功后页面上有
            config.setApiKey("");
            config.setSecretKey("");
            //Passphrase忘记后无法找回
            config.setPassphrase("");
            config.setPrint(true);
        return config;
    }

    @Bean
    public GeneralAPIService generalAPIService(APIConfiguration config) {
        return new GeneralAPIServiceImpl(config);
    }
}


```
### 4.新手教程：
```

第一步：下载并安装jdk、jre以及maven，搭建本地maven仓库

第二步：配置环境变量
2.1 配置jdk，jre，maven的环境变量

第三步：在IDEA中配置jdk以及maven

第四步：导入SDK，下载所需的maven依赖
4.1 将SDK导入到IDEA中，下载所需要的maven依赖

第五步：配置自己的API key
5.1 先去OKEx官网申请API key
5.2 将各项参数在相关文件中进行替换（Rest接口在对应的BaseTest类中替换，WS在WebSocketConfig类中替换）

第六步：调用接口
6.1 调用Rest接口，运行对应Test类中的Junit单元测试
6.2 订阅WS频道，运行对应的Test类中的Junit单元测试
