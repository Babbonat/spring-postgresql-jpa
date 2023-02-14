package it.tai.springpostresqljpa.springpostresqljpa.model;
//data model class
//corrisponde a entità e tabelle

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

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

    public Tutorial()
    {

    }

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
