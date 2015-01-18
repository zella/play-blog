package controllers;

import model.Blog;
import model.Post;
import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createblog;
import views.html.createpost;
import views.html.editpost;

import static play.data.Form.form;

/**
 * Created by dru on 14.01.2015.
 */

public class AdminsBlogs extends Controller {


//    /**
//     * Display the 'edit form' of a existing Post.
//     *
//     * @param id Id of the blog post to edit
//     */
//    public static Result edit(String id) {
//        Form<Post> blogpostForm = form(Post.class).fill(Post.findById(id));
//        return ok(editpost.render(id, Application.getLocalUser(session()), blogpostForm));
//    }

//    /**
//     * Handle the 'edit form' submission
//     *
//     * @param id Id of the blog post to edit
//     */
//    public static Result update(String id) {
//        Form<Post> blogpostForm = form(Post.class).bindFromRequest();
//        if (blogpostForm.hasErrors()) {
//            return badRequest("TODO form has error");
//        }
//        Post postFromForm = blogpostForm.get();
//
//        //TODO merge/update functionality
//        Post toUpdate = Post.findById(id);
//        toUpdate.setBody(postFromForm.getBody());
//        toUpdate.setHeader(postFromForm.getHeader());
//        toUpdate.save();
//        flash("success", "Post has been updated");
//        /**
//         * you can use redirect(routes.Application.viewPost(blogPost.save().getRid().toString()));
//         * and if you use @With(LocalUser.class) it preferred way.
//         * Only one plus of my solution - avoiding Post.findById query TODO only on create
//         */
//        return redirect(routes.Application.admin());
//    }

    /**
     * Display the 'new blog form'.
     */
    public static Result create() {
        Form<Blog> blogForm = form(Blog.class);
        return ok(createblog.render(blogForm, Application.getLocalUser(session())));
    }

    /**
     * Handle the 'new blog form' submission
     */
    public static Result save() {
        Form<Blog> blogForm = form(Blog.class).bindFromRequest();
        if (blogForm.hasErrors()) { //TODO check existance
            return badRequest(createblog.render(blogForm, Application.getLocalUser(session())));
        }
        Blog blog = blogForm.get();
        User localUser = Application.getLocalUser(session());
        blog.setUser(localUser);
        blog.save();
        flash("success", "Blog has been created");
        return redirect(routes.Application.admin());


    }

}
