package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.Comment;
import model.Post;
import model.User;
import play.Routes;
import play.data.Form;
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
        return ok(index.render(Post.findAllPosts()));
    }

    public static Result detailPost(String postId) {
        //TODO implicit user passing
        return ok(detailpost.render(Post.findById(postId), getLocalUser(session())));
    }

    @Security.Authenticated(Secured.class)
    public static Result addComment(String postId) {
        Form<Comment> commentForm = form(Comment.class).bindFromRequest();
        Comment comment = commentForm.get();
        comment.setName(getLocalUser(session()).getName());
        Post post = Post.findById(postId);
        post.addComment(comment);
        post.save();
        return ok("Ajax call!!!");
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Application.ajaxCall()
                )
        );
    }

    public static Result ajaxCall( ) {
        System.out.println("ajax");
        return ok("Ajax call!!!");
    }


    public static User getLocalUser(final Http.Session session) {
        final AuthUser identity = PlayAuthenticate.getUser(session);
        return User.findByAuthUserIdentity(identity);
    }

}
