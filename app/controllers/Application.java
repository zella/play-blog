package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import db.BlogDao;
import model.Comment;
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
        return ok(index.render(BlogDao.getAllPosts()));
    }

    @Security.Authenticated(Secured.class)
    public static Result detailpost(String postId) {
        return ok(detailpost.render(BlogDao.getPostById(postId)));
    }

    @Security.Authenticated(Secured.class)
    public static Result addcomment(String postId) {
        Form<Comment> commentForm = form(Comment.class).bindFromRequest();
        Comment comment = commentForm.get();
        BlogDao.addComment(comment, BlogDao.getPostById(postId));
        //  return redirect(controllers.routes.Application.detailpost(postId));
        return ok("ok");
    }

    public static User getLocalUser(final Http.Session session) {

        final AuthUser identity = PlayAuthenticate.getUser(session());


        if (identity instanceof EmailIdentity) {
            final EmailIdentity emailIdentity = (EmailIdentity) identity;
            final User user = BlogDao.findUserByEmail(emailIdentity.getEmail());
            return user;
        }
        return null;//TODO refactor

    }

}
