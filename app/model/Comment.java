package model;

import javax.persistence.Entity;

/**
 * Created by dru on 09.01.2015.
 */
@Entity
public class Comment {

    private User user;

    private String body;
}
