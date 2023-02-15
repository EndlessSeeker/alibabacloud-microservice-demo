package com.alibabacloud.mse.demo.a;

import com.alibabacloud.mse.demo.b.service.HelloServiceB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Api(value = "/", tags = {"入口应用"})
@RestController
class AController {

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private HelloServiceB helloServiceB;

    @Autowired
    String serviceTag;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Value("${custom.config.value}")
    private String configValue;

    private String currentZone;


    @PostConstruct
    private void init() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setConnectTimeout(1000)
                    .setSocketTimeout(1000)
                    .build();
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }
    }

    @ApiOperation(value = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/a")
    public String a(HttpServletRequest request) throws ExecutionException, InterruptedException {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }

        String result = loadBalancedRestTemplate.getForObject("http://sc-B/b", String.class);
//        String result = taskExecutor.submit(() ->
//                restTemplate.getForObject("http://sc-B/b", String.class)
//        ).get();

        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "测试防护规则" , tags = {"流量防护"})
    @GetMapping("/flow")
    public String flow(HttpServletRequest request) throws ExecutionException, InterruptedException {

        ResponseEntity<String> responseEntity = loadBalancedRestTemplate.getForEntity("http://sc-B/flow", String.class);
        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + status.value();

        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }


    @ApiOperation(value = "测试热点规则", tags = {"流量防护"})
    @GetMapping("/params/{hot}")
    public String params(HttpServletRequest request,@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        ResponseEntity<String> responseEntity = loadBalancedRestTemplate.getForEntity("http://sc-B/params/"+hot, String.class);

        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + status.value();

        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "测试隔离规则", tags = { "流量防护"})
    @GetMapping("/isolate")
    public String isolate(HttpServletRequest request) throws ExecutionException, InterruptedException {
        ResponseEntity<String> responseEntity = loadBalancedRestTemplate.getForEntity("http://sc-B/isolate", String.class);

        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + status.value();

        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }


    @GetMapping("/spring_boot")
    public String spring_boot(HttpServletRequest request) {
        String result = restTemplate.getForObject("http://sc-b:20002/spring_boot", String.class);

        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                " -> " + result;
    }

    @ApiOperation(value = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/a-zone")
    public String aZone(HttpServletRequest request) {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }
        return "A" + serviceTag + "[" + currentZone + "]" + " -> " +
                loadBalancedRestTemplate.getForObject("http://sc-B/b-zone", String.class);
    }

    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo")
    public String dubbo(HttpServletRequest request) {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("A");
    }

    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo-flow")
    public String dubbo_flow(HttpServletRequest request) {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("A");
    }

    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo-params/{hot}")
    public String dubbo_params(HttpServletRequest request, @PathVariable("hot") String hot) {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(hot);
    }

    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo-isolate")
    public String dubbo_isolate(HttpServletRequest request) {
        StringBuilder headerSb = new StringBuilder();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            Enumeration<String> val = request.getHeaders(headerName);
            while (val.hasMoreElements()) {
                String headerVal = val.nextElement();
                headerSb.append(headerName + ":" + headerVal + ",");
            }
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("isolate");
    }


    @GetMapping("swagger-demo")
    @ApiOperation(value = "这是一个演示swagger的接口 ", tags = {"首页操作页面"})
    public String swagger(@ApiParam(name = "name", value = "我是姓名", required = true) String name,
                          @ApiParam(name = "age", value = "我是年龄", required = true) int age,
                          @ApiParam(name = "aliware-products", value = "我是购买阿里云原生产品列表", required = true) List<String> aliwareProducts) {
        return "hello swagger";
    }


}
