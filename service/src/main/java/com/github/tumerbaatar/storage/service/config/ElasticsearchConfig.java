package com.github.tumerbaatar.storage.service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class ElasticsearchConfig {
    private RestHighLevelClient client;

    @Bean
    public RestHighLevelClient getClient() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
        return client;
    }

    @PreDestroy
    private void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}

