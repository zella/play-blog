package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dru on 02.08.15.
 */
@Entity
@Table(name = "posts")
public class Post extends Model {


    public static final Finder<Long,Post> find = new Finder<Long, Post>(Post.class);


    @Formats.DateTime(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @Constraints.Required
    @Id
    private String title;

    @Constraints.Required
    private String content;

    public Date getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
