import com.avaje.ebean.Ebean;
import models.user.User;
import play.*;

import play.libs.F;
import play.mvc.*;

import java.util.HashMap;
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
      User u = User.findByEmail(email);
      if (u == null) {
         u = new User(email, password, name);
         u.save();
      } else {
         u.setPassword(password);
         u.setName(name);
         u.save();
      }
   }
}