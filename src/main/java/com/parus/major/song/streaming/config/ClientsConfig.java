package com.parus.major.song.streaming.config;

import com.parus.major.song.streaming.client.SongMetadataServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientsConfig {

    @Value("${song.client.metadata-service.url}")
    private String metadataServiceUrl;

    @Bean
    public SongMetadataServiceClient songMetadataServiceClient() {
        WebClient client = WebClient.create(metadataServiceUrl);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();
        return httpServiceProxyFactory.createClient(SongMetadataServiceClient.class);
    }
}
