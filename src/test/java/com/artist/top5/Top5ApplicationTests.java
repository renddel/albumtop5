package com.artist.top5;


import com.artist.top5.controller.Top5Controller;
import com.artist.top5.model.Album;
import com.artist.top5.model.Artist;
import com.artist.top5.service.ArtistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(Top5Controller.class)
public class Top5ApplicationTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    ArtistService artistService;

    Artist artist;

    @BeforeEach
    public void setUp() {
        artist = new Artist();
        artist.setArtistId(351483001L);
        artist.setArtistName("Cryptex");
        artist.setWrapperType("artist");
        artist.setArtistType("Artist");
        artist.setArtistLinkUrl("https://music.apple.com/us/artist/cryptex/351483001?uo=4");
        artist.setAmgArtistId(2386398L);
        artist.setPrimaryGenreId(1155L);
        artist.setPrimaryGenreName("Prog-Rock/Art Rock");
    }

    @Test
    public void getArtistTest() throws Exception {
        when(artistService.getOne(anyLong())).thenReturn(artist);
        mvc.perform(get("/api/getArtist/{id}", 351483001L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistId", is(351483001)))
                .andExpect(jsonPath("$.artistName", is("Cryptex")))
                .andExpect(jsonPath("$.wrapperType", is("artist")))
                .andExpect(jsonPath("$.artistType", is("Artist")))
                .andExpect(jsonPath("$.artistLinkUrl", is("https://music.apple.com/us/artist/cryptex/351483001?uo=4")))
                .andExpect(jsonPath("$.amgArtistId", is(2386398)))
                .andExpect(jsonPath("$.primaryGenreName", is("Prog-Rock/Art Rock")))
                .andExpect(jsonPath("$.primaryGenreId", is(1155)));
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Mockito.when(artistService.create(Mockito.any(Artist.class))).thenReturn(artist);
        mvc.perform(MockMvcRequestBuilders.post("/api")
                .content(asJsonString(artist))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void getTop5AlbumTest() throws Exception {

        // a more elegant solution should be available
        Album album1 = new Album();
        album1.setCollectionId(1497762398L);
        album1.setWrapperType("collection");
        album1.setCollectionType("Album");
        album1.setArtistId(351483001L);
        album1.setAmgArtistId(2386398L);
        album1.setCollectionPrice(9.99);
        album1.setTrackCount(13);

        Album album2 = new Album();
        album2.setCollectionId(1458426164L);
        album2.setWrapperType("collection");
        album2.setCollectionType("Album");
        album2.setArtistId(351483001L);
        album2.setAmgArtistId(2386398L);
        album2.setCollectionPrice(9.99);
        album2.setTrackCount(12);

        ArrayList<Album> arr = new ArrayList<>();
        arr.add(album1);
        arr.add(album2);

        mvc.perform(MockMvcRequestBuilders.get("/api/getTop5Album/{artistId}",2386398L)
                .content(asJsonString(arr))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].collectionId",is(1497762398)))
                .andExpect(jsonPath("$[0].wrapperType",is("collection")))
                .andExpect(jsonPath("$[0].collectionType",is("Album")))
                .andExpect(jsonPath("$[0].artistId",is(351483001)))
                .andExpect(jsonPath("$[0].amgArtistId",is(2386398)))
                .andExpect(jsonPath("$[0].collectionPrice",is(9.99)))
                .andExpect(jsonPath("$[0].trackCount",is(13)))
                .andExpect(jsonPath("$[1].collectionId",is(1458426164)))
                .andExpect(jsonPath("$[1].wrapperType",is("collection")))
                .andExpect(jsonPath("$[1].collectionType",is("Album")))
                .andExpect(jsonPath("$[1].artistId",is(351483001)))
                .andExpect(jsonPath("$[1].amgArtistId",is(2386398)))
                .andExpect(jsonPath("$[1].collectionPrice",is(9.99)))
                .andExpect(jsonPath("$[1].trackCount",is(12)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
