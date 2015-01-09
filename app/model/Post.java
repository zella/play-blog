package model;

/**
 * Created by dru on 09.01.2015.
 */

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * Entity managed by JPA
 */
@Entity
public class Post {

    @Id
    //TODO increment
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header;
    private String body;
    //TODO
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Post(String body, String header) {
        this.body = body;
        this.header = header;
    }
}
