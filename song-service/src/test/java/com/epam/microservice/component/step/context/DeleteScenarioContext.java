package com.epam.microservice.component.step.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class DeleteScenarioContext {
    private List<Long> existedSongIds;
    private ResponseEntity<Map> response;
}
