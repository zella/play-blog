package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.*;
import model.User;
import play.Routes;
import play.data.Form;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import static play.data.Form.form;


/**
 * Created by dru on 14.01.2015.
 */
/**
 * Created by dru on 16.01.2015.
 */
public class Posts extends Controller{


    public static Result viewPost(String postId) {
        //TODO implicit user passing?
        return ok(post.render(Post.findById(postId), Application.getLocalUser(session())));
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
        comment.setName(Application.getLocalUser(session()).getName());
        Post post = Post.findById(postId);
        post.getComments().add(comment);
        post.save();
        return ok(Json.toJson(comment));
    }
}
