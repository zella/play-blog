package models;

import com.avaje.ebean.Model;
import models.user.User;
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


   public static final Finder<Long, Post> find = new Finder<>(Post.class);

   //TODO created and update - separate dates
   @Formats.DateTime(pattern = "yyyy-MM-dd hh:mm")
   private Date creationDate;

   @Constraints.Required
   @Id
   private String title;

   @Constraints.Required
   private String content;

   //TODO class
   @Constraints.Required
   private User user;

   public Date getCreationDate() {
      return creationDate;
   }

   public String getTitle() {
      return title;
   }

   public String getContent() {
      return content;
   }

   public User getUser() {
      return user;
   }
}
