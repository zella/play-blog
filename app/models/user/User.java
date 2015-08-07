package models.user;


import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import models.Post;
import models.user.LinkedAccount;

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
   private UUID id;

   private String email;

   private String avatarUrl;

   //TODO
   @OneToMany
   public List<Post> posts = new ArrayList<>();

   // must cascade the save; user.save() fires saving all linked accounts.
   // Note that you can update OrderDetails individually (without relying on cascade save)
   // but to insert a new OrderDetail we are relying on the cascading save.
 //  @OneToMany(cascade = CascadeType.ALL)
   public List<LinkedAccount> linkedAccounts = new ArrayList<>();

   public UUID getId() {
      return id;
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

   public List<LinkedAccount> getLinkedAccounts() {
      return linkedAccounts;
   }

   public void setLinkedAccounts(List<LinkedAccount> linkedAccounts) {
      this.linkedAccounts = linkedAccounts;
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

   public static User createAndSave(final AuthUser authUser) {
      final User user = new User();
      user.linkedAccounts = Collections.singletonList(LinkedAccount
          .create(authUser));

      if (authUser instanceof EmailIdentity) {
         final EmailIdentity identity = (EmailIdentity) authUser;
         // Remember, even when getting them from FB & Co., emails should be
         // verified within the application as a security breach there might
         // break your security as well!
         user.email = identity.getEmail();
      }

      if (authUser instanceof NameIdentity) {
         final NameIdentity identity = (NameIdentity) authUser;
         final String name = identity.getName();
         if (name != null) {
            user.name = name;
         }
      }
      user.save();
      return user;
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

   public static User find(final AuthUserIdentity authUserIdentity) {
      if (authUserIdentity == null) return null;
      return find.query()
//          .where().and(
//              Expr.eq("linkedAccounts.providerUserId", authUserIdentity.getId()),
//              Expr.eq("linkedAccounts.providerKey", authUserIdentity.getProvider())
      //    )
      .findUnique();
   }

   public static boolean exists(final AuthUserIdentity identity) {
      return find(identity) != null;
   }
}
