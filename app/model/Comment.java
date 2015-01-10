package model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by dru on 09.01.2015.
 */

public class Comment {

//    @Id
//    private Object rid;

    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
