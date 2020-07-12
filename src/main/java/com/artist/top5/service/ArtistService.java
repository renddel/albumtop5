package com.artist.top5.service;

import com.artist.top5.model.Artist;
import com.artist.top5.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    public Artist getOne(Long id){
        return artistRepository.findById(id).get();
    }

    public Artist create(Artist artist){
        artistRepository.save(artist);
        return artist;
    }

    public List<Artist> getAll(){
        return artistRepository.findAll();
    }
}
