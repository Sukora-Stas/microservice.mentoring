package com.epam.microservice.controller;

import com.epam.microservice.model.Song;
import com.epam.microservice.service.SongService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping("/{id}")
    public ResponseEntity<Song> get(@PathVariable Long id) {
        return ResponseEntity.ok(songService.findById(id));
    }

    @GetMapping
    public Song getSongByResourceId(@RequestParam Long resourceId) {
        return songService.findByResourceId(resourceId);
    }

   /* @GetMapping
    public List<Song> getAllSongs() {
        return songService.findAll();
    }*/

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Song song) {
        return ResponseEntity.ok(Map.of("id", songService.save(song)));
    }

    @DeleteMapping(params = "ids")
    public ResponseEntity<Map<String, Long[]>> delete(@RequestParam @Length(max = 200) String ids) {
        var deletedIds =
                songService.delete(Arrays.stream(ids.split(","))
                        //.map(String::trim)
                        .map(Long::parseLong)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok(Map.of("ids", deletedIds));
    }
}
