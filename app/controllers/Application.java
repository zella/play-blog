package controllers;

import models.*;
import models.user.User;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

   public static Result index(int page) {
      if (page < 1)
         return redirect(routes.Application.index(1));
      return ok(index.render(Post.page(page)));
   }


   public static Result login() {
      return ok(
            login.render(Form.form(Login.class))
      );
   }

   public static Result logout() {
      session().clear();
      flash("success", "You've been logged out");
      return redirect(
            routes.Application.login()
      );
   }

   public static Result authenticate() {
      Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
      if (loginForm.hasErrors()) {
         return badRequest(login.render(loginForm));
      } else {
         session().clear();
         session("email", loginForm.get().email);
         return redirect(
               routes.Application.admin()
         );
      }
   }

   /**
    * Display admin page
    *
    * @return
    */
   @Security.Authenticated(Secured.class)
   public static Result admin() {
      return ok(admin.render(Post.find.order().desc("creationDate").findList()));
   }


   public static class Login {

      public String email;
      public String password;

      public String validate() {
         if (!User.exist(email, password)) {
            return "Invalid user or password";
         }
         return null;
      }

   }


   public static User getLocalUser(Http.Session session){
     return User.findByEmail(session.get("email"));
   }


}
