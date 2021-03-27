package com.fede.JavaSpringPlayground.external.anime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fede.JavaSpringPlayground.RestConfiguration;
import com.fede.JavaSpringPlayground.external.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;

@Service
public class AnimeService {

    private final RestConfiguration restConfiguration;

    private final RestTemplate restTemplate;

    @Autowired
    AnimeService(RestConfiguration restConfiguration, RestTemplate restTemplate) {
        this.restConfiguration = restConfiguration;
        this.restTemplate = restTemplate;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(restConfiguration.getAnimeServiceUrl())
                .setConnectTimeout(Duration.ofMillis(restConfiguration.getServiceTimeout()))
                .setReadTimeout(Duration.ofMillis(restConfiguration.getServiceTimeout()))
                .build();
    }

    public List<Anime> searchAnimeWith(String name) throws ExternalServiceException {
        final String url = MessageFormat.format("/search/anime?q={0}", name);
        try {
            final AnimeResponse response = restTemplate.getForObject(url, AnimeResponse.class);
            return response.getAnimes();
        } catch (HttpClientErrorException httpClientErrorException) {
            final ErrorResponse errorResponse;
            try {
                errorResponse = new ObjectMapper().readValue(httpClientErrorException.getResponseBodyAsString(), ErrorResponse.class);
            } catch (JsonProcessingException e) {
                throw new ExternalServiceException(500, "Could not deserialize JSON response: " + httpClientErrorException.getResponseBodyAsString());
            }
            throw new ExternalServiceException(Integer.valueOf(errorResponse.getStatus()), errorResponse.getMessage());
        } catch (ResourceAccessException resourceAccessException) {

            //TODO: Check cause instance
            throw new ExternalServiceException(408, "Exceeded timeout of " + restConfiguration.getServiceTimeout() + "ms");
        }
    }

}


