package com.fede.JavaSpringPlayground;

import com.fede.JavaSpringPlayground.external.ExternalServiceException;
import com.fede.JavaSpringPlayground.external.anime.Anime;
import com.fede.JavaSpringPlayground.external.anime.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fede/api/v1/")
public class Router {

    private AnimeService animeService;

    @Autowired
    public Router(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/anime")
    public ResponseEntity<List<Anime>> searchAnime(@RequestParam String name) {
        try {
            return ResponseEntity.ok(animeService.searchAnimeWith(name));
        } catch (ExternalServiceException externalServiceException) {
            return new ResponseEntity(externalServiceException.getMessage(),
                    HttpStatus.valueOf(externalServiceException.getCode()));
        }
    }

}
