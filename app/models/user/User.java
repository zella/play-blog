package models.user;


import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import models.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by dru on 09.01.2015.
 */

@Entity
@Table(name = "users")
public class User extends Model {

   public static final Finder<String, User> find = new Finder<>(User.class);

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


//
//    public static User findById(String id) {
//        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
//            //TODO typing?
//            return db.load(new ORecordId(id));
//        }
//    }

   public static User findByEmail(final String email) {
      return find.query()
            .where().eq("email", email).findUnique();

   }

   public static User findByEmailAndPass(final String email, final String password) {
      List<User> users = find.all();
      User user = find.where()
            .and(Expr.eq("email", email), Expr.eq("password", password))
                  //TODO exist dquery
            .findUnique();

      return user;
   }

   public static boolean exist(final String email, final String password) {

      //  if (email.equals("1")) return true; else  return false;
      return findByEmailAndPass(email, password) != null;
   }

}
