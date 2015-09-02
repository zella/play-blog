package models.user;


import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import models.Post;

import javax.persistence.*;
import java.util.*;

/**
 * Created by dru on 09.01.2015.
 */

@Entity
@Table(name = "users")
public class User extends Model {

   public static final Finder<String, User> finder = new Finder<>(User.class);

   private String name;

   @Id
   private String email;

   private String password;

   private String avatarUrl;

   //TODO
   @OneToMany
   public List<Post> posts = new ArrayList<>();

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
}
