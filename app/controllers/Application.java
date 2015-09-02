package controllers;

import dao.BlogPostDao;
import dao.UserDao;
import models.*;
import models.user.User;
import play.data.Form;
import play.mvc.*;
import play.Routes;

import views.html.*;

public class Application extends Controller {

   public static Result index(int page) {
      if (page < 1)
         return redirect(routes.Application.index(1));
      return ok(index.render(Post.page(page, getLocalUser(session()) != null), BlogPostDao.findAllWithTitlesOnly()));
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
      return ok(admin.render(BlogPostDao.findAllWithTitlesOnly()));
   }

   /**
    * Need for referencing routes from js;
    *
    * @return
    */
   public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
          Routes.javascriptRouter("jsRoutes",
              controllers.routes.javascript.Posts.delete()
          )
      );
   }


   public static class Login {

      public String email;
      public String password;

      public String validate() {
         if (!UserDao.exist(email, password)) {
            return "Invalid user or password";
         }
         return null;
      }

   }


   public static User getLocalUser(Http.Session session) {
      return UserDao.findByEmail(session.get("email"));
   }


}
