package it.tai.springpostresqljpa.springpostresqljpa.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tutorial_details")
public class TutorialDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private Date createdOn;
    @Column
    private String createdBy;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    public TutorialDetails()
    {

    }

    public TutorialDetails(String createdBy) {
        this.createdOn = new Date();
        this.createdBy = createdBy;
    }
}
