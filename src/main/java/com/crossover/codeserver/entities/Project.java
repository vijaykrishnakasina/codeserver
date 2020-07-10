package com.crossover.codeserver.entities;

import java.time.Instant;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "project")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "external_id", nullable = false)
    @NotNull
    public String externalId;

    @Column(name = "name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "sdlc_system_id")
    @NotNull
    public SdlcSystem sdlcSystem;
    
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    public Instant createdDate;

    @Column(name = "last_modified_date", nullable = false)
    @LastModifiedDate
    public Instant lastModifiedDate;
}
