package com.fede.JavaSpringPlayground.external.anime;

import com.fede.JavaSpringPlayground.RestConfiguration;
import com.fede.JavaSpringPlayground.external.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTests {

    @Mock
    private RestConfiguration restConfiguration;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AnimeService animeService;

    @Test
    public void animeServiceOk() throws ExternalServiceException {
        final AnimeResponse response = new AnimeResponse();
        final List<Anime> animes = new ArrayList<>();
        animes.add(new Anime());
        response.setAnimes(animes);
        when(restTemplate.getForObject("/search/anime?q=naruto", AnimeResponse.class)).thenReturn(response);
        assertThat(animeService.searchAnimeWith("naruto")).isEqualTo(animes);
    }

    @Test
    public void animeServiceFails() {
        when(restTemplate.getForObject("/search/anime?q=naruto", AnimeResponse.class))
                .thenThrow(new ExternalServiceException(1, "error"));
        assertThrows(ExternalServiceException.class, () -> animeService.searchAnimeWith("naruto"));
    }

}
