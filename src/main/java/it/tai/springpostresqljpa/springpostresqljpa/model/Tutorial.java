package it.tai.springpostresqljpa.springpostresqljpa.model;
//data model class
//corrisponde a entità e tabelle

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity                                             //persistent java class
@Table(name = "tutorials")                          //annotation provides the table that maps this entity
public class Tutorial
{
    @Id                                             //primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //used to define strategy for the PK. GenerationType.AUTO means Auto Increment field
    private long id;
    @Column(name = "title")                         //define the column in database that maps annotated field
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
    private Set<Tag> tags = new HashSet<>();

    public Tutorial()
    {

    }

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public void addTag(Tag tag)
    {
        this.tags.add(tag);
        tag.getTutorials().add(this);
    }

    public void removeTag(long tagId)
    {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if(tag!=null)
        {
            this.tags.remove(tag);
            tag.getTutorials().remove(this);
        }
    }
}
