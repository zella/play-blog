package models;

import com.avaje.ebean.*;
import com.avaje.ebean.Query;
import dao.BlogPostDao;
import models.user.User;
import org.springframework.core.annotation.Order;
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


   public static final Finder<UUID, Post> finder = new Finder<>(Post.class);

   @Id
   private UUID id;

   //TODO set column names manually

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

   @Lob
   private String htmlPreview = "";

   @ManyToOne
   private User user;

   private Boolean isPrivate = false;

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

   public Boolean getIsPrivate() {
      return isPrivate;
   }

   public void setIsPrivate(Boolean isPrivate) {
      this.isPrivate = isPrivate;
   }

   public String getHtmlPreview() {
      return htmlPreview;
   }

   public void setHtmlPreview(String htmlPreview) {
      this.htmlPreview = htmlPreview;
   }

   public static Page page(int page, boolean showPrivate) {

      long total = BlogPostDao.count(showPrivate);

      List<Post> postsOnPage = BlogPostDao.page(page, Page.DEFAULT_PAGE_SIZE, showPrivate);

      return new Page(postsOnPage, total, page);
   }


   /**
    * Form validation
    *
    * @return
    */
   public List<ValidationError> validate() {
      List<ValidationError> errors = new ArrayList<>();
      if (getId() != null && !BlogPostDao.isPostTitleUnique(getTitle())) {
         errors.add(new ValidationError("title", "Post with title " + getTitle() + " already exist."));
      }
      return errors.isEmpty() ? null : errors;

   }
}
