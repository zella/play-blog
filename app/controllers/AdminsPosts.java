package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import model.Blog;
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

/**
 * Created by dru on 14.01.2015.
 */

@Security.Authenticated(Secured.class)
public class AdminsPosts extends Controller {


    /**
     * Display the 'edit form' of a existing Post.
     *
     * @param postId Id of the blog post to edit
     */
    public static Result edit(String postId, String blogName) {
        Form<Post> blogpostForm = form(Post.class).fill(Post.findById(postId));
        return ok(editpost.render(postId, blogpostForm, blogName));
    }

    /**
     * Handle the 'edit form' submission
     *
     * @param postId Id of the blog post to edit
     */
    public static Result update(String postId_, String blogName) {
        Form<Post> blogpostForm = form(Post.class).bindFromRequest();
        if (blogpostForm.hasErrors()) {
            return badRequest(editpost.render(postId_, blogpostForm, blogName));
        }
        Post postFromForm = blogpostForm.get();
        //TODO info about blog
        //TODO merge/update functionality
        Post toUpdate = Post.findById(postId_);
        toUpdate.setBody(postFromForm.getBody());
        //    toUpdate.setHeader(postFromForm.getHeader());
        toUpdate.setName(postFromForm.getName());//TODO test
        toUpdate.save();
        flash("success", "Post has been updated");
        /**
         * you can use redirect(routes.Application.viewPost(blogPost.save().getRid().toString()));
         * and if you use @With(LocalUser.class) it preferred way.
         * Only one plus of my solution - avoiding Post.findById query TODO only on create
         */
        return redirect(routes.Application.admin());
    }

    public static Result delete(String postId) {
        Post toDelete = Post.findById(postId);
        toDelete.delete();

        flash("success", "Post has been deleted");

        return redirect(routes.Application.admin());
    }

    /**
     * Display the 'new blog post form'.
     */
    public static Result create(String blogName) {
        Form<Post> blogpostForm = form(Post.class);
        return ok(createpost.render(blogpostForm, blogName));
    }

    /**
     * Handle the 'new blog post form' submission
     */
    public static Result save(String blogName_) {
        //TODO formatters issue
        Form<Post> blogpostForm = form(Post.class).bindFromRequest();//
        if (blogpostForm.hasErrors()) {
            return badRequest(createpost.render(blogpostForm, blogName_));
        }
        Post blogPost = blogpostForm.get();
        User localUser = Application.getLocalUser(session());
        blogPost.setUser(localUser);
        Blog blog = Blog.findByName(blogName_);

        blogPost.setBlog(blog);
        blogPost.save();
        flash("success", "Post for \"" + blog.getName() + "\" has been created");
        return redirect(routes.Application.admin());


    }

}
