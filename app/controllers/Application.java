package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.Comment;
import model.Post;
import model.User;
import play.Routes;
import play.data.Form;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import static play.data.Form.*;

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
    public static Result admin() {
        User localUser = getLocalUser(session());
        return ok(admin.render(Post.findByUserId(localUser.getRid().toString()), localUser));
    }


    public static Result detailPost(String postId) {
        //TODO implicit user passing?
        return ok(detailpost.render(Post.findById(postId), getLocalUser(session())));
    }

    /**
     * Ajax action
     *
     * @param postId
     * @return
     */
    @Security.Authenticated(Secured.class)
    public static Result addComment(String postId) {
        Form<Comment> commentForm = form(Comment.class).bindFromRequest();
        Comment comment = commentForm.get();
        comment.setName(getLocalUser(session()).getName());
        Post post = Post.findById(postId);
        post.getComments().add(comment);
        post.save();
        return ok(Json.toJson(comment));
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
                        controllers.routes.javascript.Application.addComment()
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
