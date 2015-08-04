import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;
import play.*;
import play.api.mvc.Handler;
import play.core.j.JavaHandler;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;

import java.lang.reflect.Method;

import play.*;
import play.mvc.*;

/**
 * Created by dru on 03.08.15.
 */
public class Global extends GlobalSettings {

   @Override
   public F.Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
      //TODO not tested
      return F.Promise.<Result>pure(play.mvc.Results.notFound());
   }

   @Override
   public void onStart(Application app) {
      super.onStart(app);

      PlayAuthenticate.setResolver(new PlayAuthenticate.Resolver() {

         @Override
         public Call login() {
            // Your login page
            return routes.Application.login();
         }

         @Override
         public Call afterAuth() {
            // The user will be redirected to this page after authentication
            // if no original URL was saved
            return routes.Application.index();
         }

         @Override
         public Call afterLogout() {
            return routes.Application.index();
         }

         @Override
         public Call auth(final String provider) {
            // You can provide your own authentication implementation,
            // however the default should be sufficient for most cases
            return com.feth.play.module.pa.controllers.routes.Authenticate
                .authenticate(provider);
         }

         @Override
         public Call onException(final AuthException e) {
            if (e instanceof AccessDeniedException) {
               return routes.Application
                   .oAuthDenied(((AccessDeniedException) e)
                       .getProviderKey());
            }

            // more custom problem handling here...

            return super.onException(e);
         }

         @Override
         public Call askLink() {
            // We don't support moderated account linking in this sample.
            // See the play-authenticate-usage project for an example
            return null;
         }

         @Override
         public Call askMerge() {
            // We don't support moderated account merging in this sample.
            // See the play-authenticate-usage project for an example
            return null;
         }
      });


   }
}