package com.epam.microservice.repository;

import com.epam.microservice.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Boolean existsByResourceId(Long resourceId);

    Song findByResourceId(Long resourceId);

    Collection<Song> deleteAllByIdIn(Collection<Long> ids);
}
