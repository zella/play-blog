package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import models.*;
import models.user.User;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

   public static final String FLASH_MESSAGE_KEY = "message";
   public static final String FLASH_ERROR_KEY = "error";

   public static Result oAuthDenied(final String providerKey) {
      com.feth.play.module.pa.controllers.Authenticate.noCache(response());
      flash(FLASH_ERROR_KEY,
          "Login error!");
      return redirect(routes.Application.login());
   }

   public Result index() {
      return ok(index.render("Your new application is ready."));
   }


   public static Result login(String page) {

      //TODO path from config

      if (page.contains("/login")) {
         return ok(login.render());
      } else {
         return notFound();
      }
   }

   /**
    * Handles login form
    * @return
    */
   public static Result doLogin() {

      //TODO path from config

      if (page.contains("/login")) {
         return ok(login.render());
      } else {
         return notFound();
      }
   }


   /**
    * Display admin page
    *
    * @return
    */
//   @Security.Authenticated(Secured.class)
//   public static Result admin() {
//      //TODO if not logged return not found;
//   }



   public static User getLocalUser(final Http.Session session) {
      final AuthUser identity = PlayAuthenticate.getUser(session);
      return User.find(identity);
   }



}
