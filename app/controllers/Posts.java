package controllers;

import com.avaje.ebean.Ebean;
import models.Post;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class Posts extends Controller {

   public Result list() {
      List<Post> allPosts = Post.find.query().findList();
      // alternativ    Ebean.find(Post.class).findList();

      return ok(index.render(allPosts));
   }

   public Result detail(String title) {
      Post post = Post.find.query()
          .where().eq("title", title).findUnique();
      return ok(post.render(post));
   }


   /**
    * Display the 'new post form'.
    */
   public Result create() {
      Form<Post> newPostForm = Form.form(Post.class);
      return ok(createpost.render(newPostForm));
   }

   /**
    * Handle the 'new post form' submission
    */
   public static Result save() {
      Form<Post> newPostForm = Form.form(Post.class).bindFromRequest();
      if (newPostForm.hasErrors()) {
         return badRequest(createpost.render(blogpostForm));
      }
      Post post = newPostForm.get();
     // blogPost.setHtmlPreview(util.TextUtils.generateTruncateHtmlPreview(blogPost.getBody(), TRUNCATED_MEDIUM_CHAR_COUNT));
      //TODO local user!
      User localUser = Application.getLocalUser(session());
      post.setUser(localUser);
      post.setCreationDate(new Date());

//      Blog blog = Blog.findByName(blogName_);
//
//      blogPost.setBlog(blog);
//      blogPost.save();

      post.save();
      flash("success", "Post has been created");
      return redirect(controllers.routes.Application.admin());


   }


}
