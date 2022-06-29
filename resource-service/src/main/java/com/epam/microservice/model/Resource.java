package com.epam.microservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "resources")
@EntityListeners(AuditingEntityListener.class)
public class Resource implements Serializable {
    //TODO: implement lombock

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String fileName;

    @Column
    private String status;

    @CreatedDate
    @Column
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = ("updated"))
    private LocalDateTime modified;

    public Resource(String fileName, String status) {
        this.fileName = fileName;
        this.status = status;
    }

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) && Objects.equals(fileName, resource.fileName) && Objects.equals(status, resource.status) && Objects.equals(created, resource.created) && Objects.equals(modified, resource.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, status, created, modified);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    public enum ProcessingStatus {
        /**
         * Indicates that the resource newly uploaded and is not processed yet.
         */
        NONE,

        /**
         * Indicates that the resource data is processing at the moment.
         */
        PENDING,

        /**
         * Indicated that the resource data is already processed.
         */
        COMPLETE
    }
}
