package com.epam.microservice.contract;

import com.epam.microservice.SongServiceRunner;
import com.epam.microservice.controller.SongController;
import com.epam.microservice.model.Song;
import com.epam.microservice.service.SongService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = SongServiceRunner.class)
public class ContractVerifier {

    @Autowired
    SongController songController;

    @MockBean
    SongService songService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(songController);
        var song =
                Song.builder()
                        .name("We Are the Champions")
                        .artist("Queen")
                        .album("News of the World")
                        .length("3:14")
                        .resourceId(7L)
                        .year("1977")
                        .build();
        Mockito.when(songService.save(song)).thenReturn(1L);
    }
}
