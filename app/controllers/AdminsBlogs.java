package controllers;

import model.Blog;
import model.Post;
import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.createblog;
import views.html.createpost;
import views.html.editpost;

import static play.data.Form.form;

/**
 * Created by dru on 14.01.2015.
 */

@Security.Authenticated(Secured.class)
public class AdminsBlogs extends Controller {

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
