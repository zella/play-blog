import controllers.*;
import dao.impl.UserDao;
import database.DB;
import models.user.User;
import play.*;

import play.Application;
import play.libs.F;
import play.mvc.*;

import java.util.List;
import java.util.Map;


/**
 * Created by dru on 03.08.15.
 */
public class Global extends GlobalSettings {

   @Override
   public F.Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
      return F.Promise.<Result>pure(play.mvc.Results.notFound("Page not found"));
   }

   @Override
   public void onStart(Application app) {
      super.onStart(app);

      DB.createIfNotExist();

      setupUsers();
   }

   private void setupUsers() {
      List<Object> users = Play.application().configuration().getList("users"); //Object type is Map
      users.forEach(o -> {
         Map<String, String> user = (Map<String, String>) o;
         String email = user.get("email");
         String password = user.get("password");
         String name = user.get("name");
         updateUserInDb(email, password, name);
      });
   }


   private void updateUserInDb(String email, String password, String name) {
      User u = controllers.Application.userDao.findByEmail(email);
      if (u == null) {
         u = new User(email, password, name);
         controllers.Application.userDao.save(u);
      } else {
         u.setPassword(password);
         u.setName(name);
         controllers.Application.userDao.save(u);
      }
   }
}