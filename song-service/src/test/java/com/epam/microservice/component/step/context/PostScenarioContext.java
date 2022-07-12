package com.epam.microservice.component.step.context;

import com.epam.microservice.model.Song;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Setter
@Getter
public class PostScenarioContext {
    private Song request;
    private ResponseEntity<Map> response;
}
