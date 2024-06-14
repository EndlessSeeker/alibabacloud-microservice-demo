package com.alibabacloud.mse.demo.c.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@DubboService(version = "1.2.0", group = "DEFAULT_GROUP")
@RequiredArgsConstructor
public class HelloServiceCTwoImpl implements HelloServiceCTwo {

    private final DefaultMQProducer producer;

    @Autowired
    InetUtils inetUtils;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    @Override
    public String hello2(String name) {

        if (throwException) {
            throw new RuntimeException();
        }

        String value = "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " params:" + name;
        String invokerTag="";
        String userData = RpcContext.getContext().getAttachment("__microservice_tag__");
        if (!StringUtils.isEmpty(userData)) {
            invokerTag = StringUtils.substringBefore(userData,",").split("\"")[3];
        }

        try {
            Message msg = new Message();
            msg.setTopic(topic);
            msg.setBody(value.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(msg);
            String msgId = sendResult.getMsgId();
            value += ",msgId:" + msgId;
            log.info("topic:{},msgId:{},messageString:{},__microservice_tag__:{}", topic, msgId, value, StringUtils.trimToNull(invokerTag));
        } catch (Exception e) {
            log.error("send message error", e);
        }

        return value;
    }

    @Override
    public String world2(String name) {
        return  "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "] -> " + name;
    }

}
