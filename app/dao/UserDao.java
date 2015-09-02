package dao;

import com.avaje.ebean.Expr;
import models.user.User;

/**
 * Created by dru on 02.09.15.
 */
public class UserDao {

   public static User findByEmail(final String email) {
      return User.finder.query()
          .where().eq("email", email).findUnique();
   }

   public static User findByEmailAndPassword(final String email, final String password) {
      return User.finder.where()
          .and(Expr.eq("email", email), Expr.eq("password", password))
          .findUnique();
   }

   public static boolean exist(final String email, final String password) {
      return findByEmailAndPassword(email, password) != null;
   }

}
