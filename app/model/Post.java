package model;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import controllers.Application;
import db.DB;
import play.data.validation.ValidationError;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    private String name;

    //TODO make as embedded entity {
    private String body;
    //TODO }

    private User user;

    /**
     * Blog that post belongs
     */
    private Blog blog;

    @Embedded
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String body, String name, User user) {
        this.body = body;
        this.name = name;
        this.user = user;
    }

    public Object getRid() {
        return rid;
    }

//    public String getHeader() {
//        return header;
//    }

    public String getBody() {
        return body;
    }

//    public void setHeader(String header) {
//        this.header = header;
//    }

    public void setBody(String body) {
        this.body = body;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public static List<Post> findAll() {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            //TODO or sql query - test performance
            Iterator<Post> it = db.browseClass(Post.class);
            List<Post> posts = Lists.newArrayList(it);
            return posts;
        }
    }

    public static List<Post> findByUserId(String userId) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from Post where user = ?)");
            List<Post> posts = db.command(query).execute(userId);
            return posts;
        }
    }

    public static Post findById(String id) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            //TODO typing?
            return db.load(new ORecordId(id));
        }
    }

    /**
     * @return proxy with rid
     */
    public Post save() {
        if (getBlog() == null) throw new IllegalStateException("You need set blog to this post before saving");
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            return db.save(this);
        }
    }

    public void delete() {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            db.delete(this);
        }
    }

    /**
     * Check existance of name for that user
     *
     * @param name name to check
     * @return
     */
    public static boolean isNameUnique(String name, String blogName) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Post where name = ? and blog = ?");
            List<Post> posts = db.command(query).execute(name, blogName);
            return posts.isEmpty();
        }
    }

    /**
     * Form validation
     *
     * @return
     */
    public String validate() {
        //  List<ValidationError> errors = new ArrayList<ValidationError>();
        System.out.println("validate post");
        if (!isNameUnique(getName(), getBlog().getName()))
            return "post " + getName() + " already exist";
        else
            return null;
    }
}
