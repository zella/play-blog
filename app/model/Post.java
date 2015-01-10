package model;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dru on 09.01.2015.
 */





public class Post {

    @Id
    private Object rid;

    private String header;
    private String body;

    private User user;

    @Embedded
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String body, String header, User user) {
        this.body = body;
        this.header = header;
        this.user = user;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
