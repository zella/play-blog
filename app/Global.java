import play.*;
import play.api.mvc.Handler;
import play.core.j.JavaHandler;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Request;

import java.lang.reflect.Method;

/**
 * Created by dru on 03.08.15.
 */
public class Global extends GlobalSettings {

   public Action onRequest(Request request, Method actionMethod) {
      System.out.println("before each request..." + request.toString());
      //TODO debug it

      return super.onRequest(request, actionMethod);
   }

   @Override
   public Handler onRouteRequest(Http.RequestHeader request) {

      String path = request.path();

      switch (path) {
         case "/admin_path_from_conf":
            return
      }

      return super.onRouteRequest(request);
   }
}