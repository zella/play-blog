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

   public Result index(int page) {
      return ok(index.render(page, Post.page(page)));
   }


   /**
    * Login page. "with google etc"
    *
    * @return
    */
   public static Result login() {
       return ok(login.render());
   }


   /**
    * Display admin page
    *
    * @return
    */
   @Security.Authenticated(Secured.class)
   public static Result admin() {
      return ok(admin.render());
   }



   public static User getLocalUser(final Http.Session session) {
      final AuthUser identity = PlayAuthenticate.getUser(session);
      return User.find(identity);
   }



}
