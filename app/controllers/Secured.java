package controllers;

/**
 * Created by dru on 05.08.15.
 */


import models.user.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

public class Secured extends Security.Authenticator {

   @Override
   public String getUsername(final Context ctx) {
      final AuthUser authUser = PlayAuthenticate.getUser(ctx.session());

      if (authUser != null) {
         User user = User.find(authUser);
         ctx.args.put("user", user);
         return user.getName();
      } else {
         return null;
      }
   }

   @Override
   public Result onUnauthorized(final Context ctx) {
      ctx.flash().put(Application.FLASH_ERROR_KEY, "Nice try, but you need to log in first!");
      //auth protect from commenting, also from admining
      return redirect(routes.Application.login());
   }
}