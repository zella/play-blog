package controllers;

import dao.IUserDao;
import dao.IBlogPostDao;
import dao.impl.OrientBlogPostDao;
import dao.impl.OrientUserDao;
import models.*;
import models.user.User;
import play.data.Form;
import play.mvc.*;
import play.Routes;

import views.html.*;

public class Application extends Controller {

   //TODO DI ?
   public static IBlogPostDao postDao = new OrientBlogPostDao();
   public static IUserDao userDao = new OrientUserDao();

   public static Result index(int page) {
      if (page < 1)
         return redirect(routes.Application.index(1));
      boolean isLogged = getLocalUser(session()) != null;
      return ok(index.render(Post.page(page, isLogged), postDao.findAll(isLogged)));
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
      return ok(admin.render(postDao.findAll(true)));
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
                      controllers.routes.javascript.Posts.delete(),
                      controllers.routes.javascript.Posts.doEdit(),
                      controllers.routes.javascript.Posts.save()
              )
      );
   }


   public static class Login {

      private String email;
      private String password;

      public String getEmail() {
         return email;
      }

      public void setEmail(String email) {
         this.email = email;
      }

      public String getPassword() {
         return password;
      }

      public void setPassword(String password) {
         this.password = password;
      }

      public String validate() {
         if (!userDao.exist(email, password)) {
            return "Invalid user or password";
         }
         return null;
      }

   }


   public static User getLocalUser(Http.Session session) {
      if (session.get("email") == null)
         return null;
      else
         return userDao.findByEmail(session.get("email"));
   }


}
