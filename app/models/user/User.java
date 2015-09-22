package models.user;



import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dru on 09.01.2015.
 */

public class User {


   private String id;

   private String name;

   private String email;

   private String password;

   private String avatarUrl;

   public List<Post> posts = new ArrayList<>();

   public User() {
   }

   public User(String email, String password, String name) {
      this.password = password;
      this.email = email;
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public String getName() {
      return name;
   }

   public String getAvatarUrl() {
      return avatarUrl;
   }

   public void setAvatarUrl(String avatarUrl) {
      this.avatarUrl = avatarUrl;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Post> getPosts() {
      return posts;
   }

   public void setPosts(List<Post> posts) {
      this.posts = posts;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getId() {
      return id;
   }

   private void setId(String id) {
      this.id = id;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(getEmail(), user.getEmail()) &&
              Objects.equals(getPassword(), user.getPassword());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getEmail(), getPassword());
   }

   public static User fromDocument(ODocument doc) {
      if (doc == null) return null;

      User user = new User();
      user.setId(doc.getIdentity().toString());
      user.setName(doc.field("name"));
      user.setEmail(doc.field("email"));
      user.setPassword(doc.field("password"));
      user.setAvatarUrl(doc.field("avatarUrl"));
      return user;
   }

   public ODocument toDocument() {
      ODocument doc;

      if (getId() != null) {
         doc = new ODocument("User", new ORecordId(getId()));
      } else
         doc = new ODocument("User");

      doc.field("name", getName());
      doc.field("email", getEmail());
      doc.field("password", getPassword());
      doc.field("avatarUrl", getAvatarUrl());
      return doc;
   }

   @Override
   public String toString() {
      return "User{" +
              "id='" + id + '\'' +
              ", name='" + name + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              ", avatarUrl='" + avatarUrl + '\'' +
              ", posts=" + posts +
              '}';
   }
}
