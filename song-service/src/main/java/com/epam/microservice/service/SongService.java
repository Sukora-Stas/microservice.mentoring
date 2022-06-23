package com.epam.microservice.service;

import com.epam.microservice.model.Song;
import com.epam.microservice.repository.SongRepository;
import com.epam.microservice.service.exceptions.EntityDuplicateException;
import com.epam.microservice.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Song findByResourceId(Long resourceId) {
        return songRepository.findByResourceId(resourceId);
    }

    public Long save(Song song) {

        if (songRepository.existsByResourceId(song.getResourceId())) {
            throw new EntityDuplicateException(song.getResourceId());
        }

        return songRepository.save(song).getId();
    }

    @Transactional
    public Long[] delete(List<Long> ids) {
        return songRepository.deleteAllByIdIn(ids).stream().map(Song::getId).toArray(Long[]::new);
    }
}
