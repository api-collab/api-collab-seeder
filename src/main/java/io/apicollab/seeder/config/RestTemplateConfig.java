package io.apicollab.seeder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${api.portal.host:localhost}")
    String host;
    @Value("${api.portal.port:8080}")
    String port;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        String rootUri = "http://" + host + ":" + port;
        return restTemplateBuilder.rootUri(rootUri).build();
    }
}
