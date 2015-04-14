package model;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import db.DB;
import play.data.validation.Constraints;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dru on 16.01.2015.
 */
public class Blog {

    @Id
    private Object rid;
    //TODO unique field. Validation
    // @Constraints.Required
    private String name;
    /**
     * Blog belongs to
     */
    private User user;

    public Object getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Check global existance of blog name
     *
     * @param name name to check
     * @return
     */
    public static boolean isNameUnique(String name) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Blog where name = ?");
            List<Blog> blogs = db.command(query).execute(name);
            return blogs.isEmpty();
        }
    }

    public static Blog findById(String id) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            //TODO typing?
            return db.load(new ORecordId(id));
        }
    }


    public static Blog findByName(String name) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Blog where name = ?");
            List<Blog> blogs = db.command(query).execute(name);
            if (blogs.isEmpty())
                return null;
            else
                return blogs.get(0);
        }
    }

    public static List<Blog> findByUser(String userId) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Blog where user = ?");
            List<Blog> blogs = db.command(query).execute(userId);
            return blogs;
        }
    }

    public static List<Blog> findAll() {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<Blog>("select from Blog");
            List<Blog> blogs = db.command(query).execute();
            return blogs;
        }
    }

    /**
     * @return proxy with rid
     */
    public Blog save() {

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
     * Form validation
     *
     * @return
     */
    public String validate() {
        if (!isNameUnique(getName()))
            return "blog with " + getName() + " already exist";
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        if (!getName().equals(blog.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
