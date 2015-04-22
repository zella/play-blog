package controllers;

import model.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import java.util.Date;

import static play.data.Form.form;


/**
 * Created by dru on 14.01.2015.
 */

/**
 * Created by dru on 16.01.2015.
 */
public class Posts extends Controller {

    public static Result viewPost(String blogName, String postName) {
        //TODO bug in javasisst asm? Update to new version; I turned off lazy loading for resolving it
//        Post post_ = Blog.findByName(blogName).getPosts()
//                .stream().filter(p -> p.getName().equals(postName)).findFirst().get();

        return ok(post.render(Post.findByPostNameAndBlogName(postName, blogName), Application.getLocalUser(session())));
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
        comment.setDate(new Date());
        Post post = Post.findById(postId);
        post.getComments().add(comment);
        post.save();
//        return ok(Json.toJson(comment));
        return ok(views.html.comment.comment.render(comment));
    }
}
