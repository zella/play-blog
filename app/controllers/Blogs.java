package controllers;


import model.Blog;
import model.Post;
import play.mvc.*;
import play.mvc.Result;

import views.html.*;


import static play.data.Form.form;


/**
 * Created by dru on 14.01.2015.
 */

/**
 * Created by dru on 16.01.2015.
 */
public class Blogs extends Controller {

    public static Result viewBlog(String name) {
        Blog blog_ = Blog.findByName(name);
        if (blog_ != null)
            return ok(blog.render(blog_));
        else
            return forbidden("blog not found");//TODO blog not found page
    }


}
