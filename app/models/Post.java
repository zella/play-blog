package models;

import com.avaje.ebean.Model;
import models.user.User;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by dru on 02.08.15.
 */
@Entity
@Table(name = "posts")
public class Post extends Model {


   public static final Finder<UUID, Post> find = new Finder<>(Post.class);

   @Id
   private UUID id;

   //TODO created and update - separate dates
   @Formats.DateTime(pattern = "yyyy-MM-dd hh:mm")
   private Date creationDate;

   @Constraints.Required
   @Constraints.MinLength(value = 2)
   @Constraints.MaxLength(value = 255)
   private String title;

   @Constraints.Required
   @Constraints.MinLength(value = 10)
   @Lob
   private String content;

   @ManyToOne
   private User user;

   public UUID getId() {
      return id;
   }

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

   public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public static Page page(int page) {
      // 0 - should be first

     // if (page < 1) page = 1;
      long total = find.findRowCount();
      List<Post> postsOnPage = find
            .order().desc("creationDate")
            .findPagedList(page-1, Page.DEFAULT_PAGE_SIZE)
            .getList();

      return new Page(postsOnPage, total, page);
   }

   public String getHtmlPreview() {
      return "TODO";
   }

   public static boolean isTitleUnique(String title) {
      return find.query()
            .where().eq("title", title).findUnique() == null;
   }

   /**
    * Form validation
    *
    * @return
    */
   public List<ValidationError> validate() {
      List<ValidationError> errors = new ArrayList<>();
      if (getId() != null && !isTitleUnique(getTitle())) {
         errors.add(new ValidationError("title", "Post with title " + getTitle() + " already exist."));
      }
      return errors.isEmpty() ? null : errors;

   }
}
