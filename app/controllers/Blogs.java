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

    public static Result viewBlog(int page, String blogName) {
        Blog blog_ = Blog.findByName(blogName);
        if (blog_ != null)
            return ok(blog.render(Post.page(page, 3, blogName),blogName));
        else
            return forbidden("blog not found");//TODO blog not found page
    }

//    public static Result listPosts(int page, String blogName) {
//        return ok(               list.render(Post.page(page, 10, blogName)));
//    }


}
