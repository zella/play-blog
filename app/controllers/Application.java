package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.Comment;
import model.Post;
import model.User;
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

//    public static User getLocalUser(final Session session) {
//        final User localUser = User.findByAuthUserIdentity(PlayAuthenticate
//                .getUser(session));
//        return localUser;
//    }

    public static Result index() {
        return ok(index.render(Post.findAllPosts()));
    }

    public static Result detailPost(String postId) {
        return ok(detailpost.render(Post.findById(postId)));
    }

    @Security.Authenticated(Secured.class)
    public static Result addComment(String postId) {
        Form<Comment> commentForm = form(Comment.class).bindFromRequest();
        Comment comment = commentForm.get();
        Post post = Post.findById(postId);
        post.addComment(comment);
        post.save();
        return ok();
    }

    public static User getLocalUser(final Http.Session session) {
        final AuthUser identity = PlayAuthenticate.getUser(session);
        return User.findByAuthUserIdentity(identity);
    }

}
