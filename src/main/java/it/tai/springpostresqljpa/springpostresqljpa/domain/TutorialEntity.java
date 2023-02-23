package it.tai.springpostresqljpa.springpostresqljpa.domain;
//data model class
//corrisponde a entità e tabelle

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity                                             //persistent java class
@Builder
@Table(name = "tutorials")                          //annotation provides the table that maps this entity
public class TutorialEntity
{
    @Id                                             //primary key
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO) //used to define strategy for the PK. GenerationType.AUTO means Auto Increment field
    private long id;
    @Column(name = "title")                         //define the column in database that maps annotated field
    @EqualsAndHashCode.Include
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "published")
    private boolean published;

    //owning side, specifichiamo il JoinTable. Questo è il lato a cui Hibernate guarda per
    //vedere quali associazioni esistano. Per esempio, sa aggiungo un Tag nel set di tag di un
    //Tutorial, Hibernate aggiungerà una nuova riga nella tabella di join "tutorial_tags". Al
    //contrario, se aggiungo un Tutorial al set dei tutorial di un Tag niente viene modificato nel DB
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tutorial_tags",
               joinColumns = {@JoinColumn(name = "tutorial_id")},
               inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @EqualsAndHashCode.Exclude
    private Set<TagEntity> tags = new HashSet<>();

    public void addTag(TagEntity tagEntity)
    {
        this.tags.add(tagEntity);
        tagEntity.getTutorials().add(this);
    }

    public void removeTag(long tagId)
    {
        TagEntity tagEntity = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if(tagEntity !=null)
        {
            this.tags.remove(tagEntity);
            tagEntity.getTutorials().remove(this);
        }
    }
}
