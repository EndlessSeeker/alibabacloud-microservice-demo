
package com.alibabacloud.mse.demo.b;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class BApplication {

    public static void main(String[] args) {
        SpringApplication.run(BApplication.class, args);
    }

    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "serviceTag")
    String serviceTag() {
        String tag = parseServiceTag("/etc/podinfo/labels");
        if (StringUtils.isNotEmpty(tag)) {
            return tag;
        }
        tag = parseServiceTag("/etc/podinfo/annotations");
        if (StringUtils.isNotEmpty(tag)) {
            return tag;
        }
        tag = System.getenv("MSE_ALICLOUD_SERVICE_TAG");
        if (StringUtils.isNotEmpty(tag)) {
            return tag;
        }

        return "base";
    }

    private String parseServiceTag(String path) {
        String tag = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                Properties properties = new Properties();
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    properties.load(fr);
                } catch (IOException e) {
                } finally {
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (Throwable ignore) {
                        }
                    }
                }
                tag = properties.getProperty("alicloud.service.tag").replace("\"", "");
            } else {
                tag = System.getProperty("alicloud.service.tag");
            }
        } catch (Throwable ignore) {
        }

        if ("null".equalsIgnoreCase(tag) || null == tag) {
            tag = "";
        }
        return tag;
    }
}
