package com.alibabacloud.mse.demo.c.mq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RocketMqConfiguration {

    @Value("${middleware.mq.address}")
    private String nameSrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }

    @Bean(destroyMethod = "shutdown")
    public DefaultMQProducer getBaseProducer() throws Exception {
        log.info("正在启动rocketMq的producer");
        AclClientRPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(
                System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"),
                System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET")
        ));

        DefaultMQProducer baseProducer = new DefaultMQProducer(rpcHook);
        baseProducer.setProducerGroup(groupName);
        baseProducer.setNamesrvAddr(nameSrvAddr);
        baseProducer.start();
        log.info("完成启动rocketMq的producer");
        return baseProducer;
    }


}

