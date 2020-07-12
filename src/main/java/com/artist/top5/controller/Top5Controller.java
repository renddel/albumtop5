package com.artist.top5.controller;

import com.artist.top5.exceptions.RecordNotFoundException;
import com.artist.top5.model.Album;
import com.artist.top5.model.Artist;
import com.artist.top5.model.Result;
import com.artist.top5.service.ArtistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class Top5Controller {

    @Autowired
    ArtistService artistService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/getArtist/{id}")
    public Artist getArtist(@PathVariable Long id) throws JsonProcessingException, RecordNotFoundException {
        try {
            String url = "https://itunes.apple.com/lookup?id=" + id;
            ObjectMapper om = new ObjectMapper();
            ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            Result result = om.readValue(re.getBody(), Result.class);
            Artist artist = result.getResults().get(0);
            return artist;
        } catch (Exception e) {
            throw new RecordNotFoundException("There was a a problem with getArtist api");
        }
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist saveFavoriteArtist(@RequestBody final Artist artist) throws RecordNotFoundException {
        try {
            artistService.create(artist);
            return artistService.getOne(artist.getArtistId());
        } catch (Exception e) {
            throw new RecordNotFoundException("Couldn't save artist to favorites");
        }
    }

    @GetMapping
    public List<Artist> getFavoriteList() {
        try {
            return artistService.getAll();
        } catch (Exception e) {
            throw new RecordNotFoundException("Problem fetching all artist list");
        }
    }

    @GetMapping(value = "/getTop5Album/{artistId}")
    public List<Album> getTop5Album(@PathVariable Long artistId) throws JsonProcessingException, ParseException {
        try {
            String url = "https://itunes.apple.com/lookup?amgArtistId=" + artistId + "&entity=album&limit=5";
            ObjectMapper om = new ObjectMapper();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JSONObject jsonObj = new JSONObject(responseEntity.getBody());
            JSONArray featuresArr = jsonObj.getJSONArray("results");
            ArrayList<Album> list = new ArrayList<>();

            for (int i = 1; i < featuresArr.length(); i++) {
                Album album = om.readValue(featuresArr.getJSONObject(i).toString(), Album.class);
                list.add(album);
            }
            return list;
        } catch (Exception e) {
            throw new RecordNotFoundException("A problem with getTop5Album api");
        }
    }
}
