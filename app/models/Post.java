package models;

import com.avaje.ebean.*;

import com.orientechnologies.orient.core.record.impl.ODocument;
import controllers.Application;

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
public class Post  {

   private String id;

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

   public String getId() {
      return id;
   }

   public Date getCreationDate() {
      return creationDate;
   }

   private void setId(String id) {
      this.id = id;
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

      long total = Application.postDao.count(showPrivate);

      List<Post> postsOnPage = Application.postDao.page(page, Page.DEFAULT_PAGE_SIZE, showPrivate);

      return new Page(postsOnPage, total, page);
   }


   /**
    * Form validation
    *
    * @return
    */
   public List<ValidationError> validate() {
      List<ValidationError> errors = new ArrayList<>();
      if (getId() != null && !Application.postDao.isPostTitleUnique(getTitle())) {
         errors.add(new ValidationError("title", "Post with title " + getTitle() + " already exist."));
      }
      return errors.isEmpty() ? null : errors;

   }

   public static Post fromDocument(ODocument doc) {
      if (doc == null) return null;

      Post post = new Post();
      post.setId(doc.getIdentity().toString());
      post.setContent(doc.field("content"));
      post.setCreationDate(doc.field("creationDate"));
      post.setTitle(doc.field("title"));
      post.setHtmlPreview(doc.field("htmlPreview"));
      post.setIsPrivate(doc.field("isPrivate"));
      post.setUser(User.fromDocument(doc.field("user")));
      return post;
   }

   public ODocument toDocument() {
      ODocument doc = new ODocument();

      //TODO 75% - not working; need test
      if (getId() != null) {
         doc.field("rid", getId());
      }

      doc.field("content", getContent());
      doc.field("creationDate", getCreationDate());
      doc.field("title", getTitle());
      doc.field("htmlPreview", getHtmlPreview());
      doc.field("isPrivate", getIsPrivate());
      doc.field("user", getUser().toDocument());

      return doc;
   }

}
