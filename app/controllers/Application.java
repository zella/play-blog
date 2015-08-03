package controllers;

import models.Post;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

   public Result index() {
      return ok(index.render("Your new application is ready."));
   }


   public static Result admin(String page) {

      if (page.contains("abc")) {
         return ok();//TODO admin
      } else {
         return notFound();
      }
   }

}
