package model;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import db.DB;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    private List<Comment> comments;

    public Post() {
    }

    public Post(String body, String header, User user) {
        this.body = body;
        this.header = header;
        this.user = user;
    }

    public Object getRid() {
        return rid;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }


    public static Post createAndSave(final Post post, final User user) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            post.setUser(user);
            return db.save(post);
        }
    }

    public static List<Post> findAllPosts() {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            //TODO or sql query - test performance
            Iterator<Post> it = db.browseClass(Post.class);
            List<Post> posts = Lists.newArrayList(it);
            return posts;
        }
    }

    public static Post findById(String id) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            //TODO typing?
            return db.load(new ORecordId(id));
        }
    }

    public void addComment(Comment comment) {
        if (comments == null) comments = Collections.singletonList(comment);
        this.getComments().add(comment);
    }

    public void save() {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            db.save(this);
        }
    }
}
