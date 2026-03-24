package io.streamlinker.zlm.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.streamlinker.zlm.api.DefaultZlmClient;
import io.streamlinker.zlm.api.ZlmClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@EnableConfigurationProperties(ZlmProperties.class)
@ConditionalOnProperty(prefix = "streamlinker.zlm", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ZlmAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClient zlmRestClient(ZlmProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getApiUrl())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZlmClient zlmClient(RestClient zlmRestClient, ObjectMapper objectMapper, ZlmProperties properties) {
        return new DefaultZlmClient(zlmRestClient, objectMapper, properties.getSecret());
    }
}