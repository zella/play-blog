package model;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.id.ORID;
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
    private ORID rid;

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

    //seems bad. need better validation mechanism
    public void setRid(ORID rid) {
        this.rid = rid;
    }


    public String getBody() {
        return body;
    }


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

    /**
     * For test cases
     *
     * @return
     */
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

    public static Post findByPostNameAndBlogName(String postName, String blogName) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from Post where name = ? and blog.name = ?)");
            List<Post> posts = db.command(query).execute(postName, blogName);
            return posts.get(0);
        }
    }

    public static List<Post> findByBlogName(String name) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from Post where blog.name = ?)");
            List<Post> posts = db.command(query).execute(name);
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
     * Check existance of name for blog
     *
     * @param name name to check
     * @return
     */
    public static boolean isNameUnique(String name, String blogName) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Post where name = ? and blog.name = ?");
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
        //TODO epmty text validation
        System.out.println("validate post");
        //if update, name can be same
        if (getRid() != null) return null;
        if (!isNameUnique(getName(), getBlog().getName()))
            return "post " + getName() + " already exist";
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (name != null ? !name.equals(post.name) : post.name != null) return false;
        if (rid != null ? !rid.equals(post.rid) : post.rid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid != null ? rid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
