package com.epam.microservice.component.step.context;

import com.epam.microservice.model.Song;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class GetByIdScenarioContext {
    private Song existedSong;
    private ResponseEntity<Song> response;
}
