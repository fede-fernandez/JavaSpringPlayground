package com.fede.JavaSpringPlayground;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

    @Value("${api.external.anime.url}")
    private String animeServiceUrl;

    @Value("${api.external.timeout-ms}")
    private Integer serviceTimeout;

    public String getAnimeServiceUrl() {
        return animeServiceUrl;
    }

    public void setAnimeServiceUrl(String animeServiceUrl) {
        this.animeServiceUrl = animeServiceUrl;
    }

    public Integer getServiceTimeout() {
        return serviceTimeout;
    }

    public void setServiceTimeout(Integer serviceTimeout) {
        this.serviceTimeout = serviceTimeout;
    }

}
