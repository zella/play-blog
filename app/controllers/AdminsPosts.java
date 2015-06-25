package controllers;

import model.Blog;
import model.Post;
import model.User;
import play.data.Form;
import play.mvc.*;
import util.TextUtils;
import views.html.*;

import java.util.Date;

import static play.data.Form.*;

/**
 * Created by dru on 14.01.2015.
 */

@Security.Authenticated(Secured.class)
public class AdminsPosts extends Controller {


    public static final int TRUNCATED_MEDIUM_CHAR_COUNT = 150;

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
     * @param postId_ Id of the blog post to edit
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
        toUpdate.setName(postFromForm.getName());
        toUpdate.setHtmlPreview(TextUtils.generateTruncateHtmlPreview(toUpdate.getBody(), TRUNCATED_MEDIUM_CHAR_COUNT));
        toUpdate.save();
        flash("success", "Post has been updated");
        /**
         * you can use redirect(routes.Application.viewPost(blogPost.save().getRid().toString()));
         * and if you use @With(LocalUser.class) it preferred way.
         * Only one plus of my solution - avoiding Post.findById query TODO only on create
         */
        return redirect(controllers.routes.Application.admin());
    }

    public static Result delete(String postId) {
        Post toDelete = Post.findById(postId);
        toDelete.delete();

        flash("success", "Post has been deleted");

        return redirect(controllers.routes.Application.admin());
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
        blogPost.setHtmlPreview(TextUtils.generateTruncateHtmlPreview(blogPost.getBody(), 50));
        User localUser = Application.getLocalUser(session());
        blogPost.setUser(localUser);
        blogPost.setCreationDate(new Date());

        Blog blog = Blog.findByName(blogName_);

        blogPost.setBlog(blog);
        blogPost.save();
        flash("success", "Post for \"" + blog.getName() + "\" has been created");
        return redirect(routes.Application.admin());


    }


}
