package models;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import controllers.Application;
import models.user.User;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.List;

/**
 * Created by dru on 02.08.15.
 */
public class Post  {

   private String id;

   //TODO created and update - separate dates
   private Date creationDate;

   @Constraints.Required
   @Constraints.MinLength(value = 2)
   @Constraints.MaxLength(value = 255)
   private String title;

   private String content;

   @Constraints.Required
   @Constraints.MinLength(value = 10)
   private String mdContent;


   private String htmlPreview = "";

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

   /**
    *
    * @return content in Html
    */
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

   /**
    *
    * @return content in Markdown
     */
   public String getMdContent() {
      return mdContent;
   }

   public void setMdContent(String mdContent) {
      this.mdContent = mdContent;
   }

   public static Page page(int page, boolean showPrivate) {

      long total = Application.postDao.count(showPrivate);

      List<Post> postsOnPage = Application.postDao.page(page, Page.DEFAULT_PAGE_SIZE, showPrivate);

      return new Page(postsOnPage, total, page);
   }

   public static Post fromDocument(ODocument doc) {
      if (doc == null) return null;

      Post post = new Post();
      post.setId(doc.getIdentity().toString());
      post.setContent(doc.field("content"));
      post.setMdContent(doc.field("mdContent"));
      post.setCreationDate(doc.field("creationDate"));
      post.setTitle(doc.field("title"));
      post.setHtmlPreview(doc.field("htmlPreview"));
      post.setIsPrivate(doc.field("isPrivate"));
      //FIXME error if user not exist
      post.setUser(User.fromDocument(doc.field("user")));
      return post;
   }

   public ODocument toDocument() {

      ODocument doc;

      if (getId() != null) {
         doc = new ODocument("BlogPost", new ORecordId(getId()));
      } else
         doc = new ODocument("BlogPost");

      doc.field("content", getContent());
      doc.field("mdContent", getMdContent());
      doc.field("creationDate", getCreationDate());
      doc.field("title", getTitle());
      doc.field("htmlPreview", getHtmlPreview());
      doc.field("isPrivate", getIsPrivate());
      doc.field("user", getUser().toDocument());

      return doc;
   }

   @Override
   public String toString() {
      return "Post{" +
              "id='" + id + '\'' +
              ", creationDate=" + creationDate +
              ", title='" + title + '\'' +
              ", user=" + user +
              ", isPrivate=" + isPrivate +
              '}';
   }
}
