package it.tai.springpostresqljpa.springpostresqljpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "comments")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutorial_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Tutorial tutorial;

    /*Now we can see the pros of @ManyToOne annotation.
        – With @OneToMany, we need to declare a collection inside parent class,
        we cannot limit the size of that collection, for example, in case
        of pagination.
        – With @ManyToOne, you can modify Repository:
          to work with Pagination or to sort/order by multiple fields*/
}
