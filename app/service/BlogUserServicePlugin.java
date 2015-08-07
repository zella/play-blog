package service;


import com.google.inject.Inject;
import models.user.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

/**
 * Authenticate
 */
public class BlogUserServicePlugin extends UserServicePlugin {

   @Inject
   public BlogUserServicePlugin(final Application app) {
      super(app);
   }

   @Override
   public Object save(final AuthUser authUser) {
      System.out.println("save_user");
      if (User.exists(authUser)) return null;
      return User.createAndSave(authUser).getId();
   }

   @Override
   public Object getLocalIdentity(final AuthUserIdentity identity) {
      System.out.println("local_identity");
      final User user = User.find(identity);
      if (user != null)
         return user.getId();
      else
         return null;

   }

   @Override
   public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
      return oldUser;
   }

   @Override
   public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
      //Not supported yet
      return null;
   }

}
