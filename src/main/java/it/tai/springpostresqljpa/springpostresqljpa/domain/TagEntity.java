package it.tai.springpostresqljpa.springpostresqljpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tags", uniqueConstraints = {@UniqueConstraint(name = "name_uq", columnNames = {"name"})})
public class TagEntity
{
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;

    //non-owning side. Dobbiamo usare "mappedBy" per specificare il campo che lega Tag al Tutorial
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<TutorialEntity> tutorials = new HashSet<>();
}
