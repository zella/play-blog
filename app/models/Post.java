package models;

import com.avaje.ebean.Model;
import models.user.User;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
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
   private String title;

   @Constraints.Required
   @Column(columnDefinition = "TEXT")
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
            .order().asc("creationDate")
            .findPagedList(page, Page.DEFAULT_PAGE_SIZE)
            .getList();

      return new Page(postsOnPage, total, page);
   }

   public String getHtmlPreview() {
      return "TODO";
   }

}
