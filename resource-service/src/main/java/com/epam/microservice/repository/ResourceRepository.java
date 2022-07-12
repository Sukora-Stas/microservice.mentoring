package com.epam.microservice.repository;

import com.epam.microservice.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Boolean existsByFileName(String filename);
    List<Resource> deleteAllByIdIn(Collection<Long> ids);

    List<Resource> findAllBySent(Integer sent);
}
