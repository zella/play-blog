package model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by dru on 09.01.2015.
 */

public class Comment {

//    @Id
//    private Object rid;

    private User user;
    private String body;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
