package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.Blog;
import model.Post;
import model.User;
import play.Routes;
import play.libs.F;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";

    public static Result oAuthDenied(final String providerKey) {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        flash(FLASH_ERROR_KEY,
                "You need to accept the OAuth connection in order to use this website!");
        return redirect(routes.Application.index());
    }

    public static Result index() {
        return ok(index.render(Post.findAll()));
    }
    /**
     * Display admin page
     *
     * @return
     */
    @Security.Authenticated(Secured.class)
    public static Result admin() {
        User localUser = getLocalUser(session());
        return ok(admin.render(Blog.findByUser(localUser.getRid().toString()), localUser));
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
                        controllers.routes.javascript.Application.ajaxCall(),
                        controllers.routes.javascript.Posts.addComment()
                )
        );
    }

    public static Result ajaxCall() {
        System.out.println("ajax");
        return ok("Ajax call!!!");
    }


    public static User getLocalUser(final Http.Session session) {
        final AuthUser identity = PlayAuthenticate.getUser(session);
        return User.findByAuthUserIdentity(identity);
    }

    /**
     * Avoiding passing user to views. @see <a href="http://stackoverflow.com/questions/9629250/how-to-avoid-passing-parameters-everywhere-in-play2">detail answer on so</a>
     * You can also use {@link #getLocalUser(play.mvc.Http.Session)}
     * Not sure use it or not. See in future
     */
    public static class LocalUser extends Action.Simple {

        public F.Promise<Result> call(Http.Context ctx) throws Throwable {
            ctx.args.put("user", getLocalUser(session()));
            return delegate.call(ctx);
        }

//        public static List<Menu> current() {
//            return (List<Menu>)Http.Context.current().args.get("menus");
//        }

        public static User get() {
            return (User) Http.Context.current().args.get("user");
        }
    }

}
