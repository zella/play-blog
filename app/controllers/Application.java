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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Post> posts = Post.findByUserId(localUser.getRid().toString());
        //TODO if posts empty show blogs

        List<Blog> blogs = Blog.findByUser(localUser.getRid().toString());

        Map<Blog, List<Post>> groupByBlog = posts
                .stream().collect(
                        Collectors.groupingBy(p -> p.getBlog()));

        //TODO stream
        blogs.forEach(b -> {
            if (!groupByBlog.containsKey(b)) {
                groupByBlog.put(b, new ArrayList<Post>());
            }
        });


        return ok(admin.render(groupByBlog, localUser));

//
//        User localUser = getLocalUser(session());
//        return ok(admin.render(Blog.findByUser(localUser.getRid().toString()), localUser));
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

    //TODO remove
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
