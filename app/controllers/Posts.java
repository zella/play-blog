package controllers;

import com.avaje.ebean.Ebean;
import models.Post;
import models.user.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.editpost;
import views.html.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static play.data.Form.form;

public class Posts extends Controller {

//   public static Result list() {
//      List<Post> allPosts = Post.find.query().findList();
//      // alternativ    Ebean.find(Post.class).findList();
//
//      return ok(index.render(allPosts));
//   }

   public static Result detail(String title) {
      Post post_ = Post.find.query()
            .where().eq("title", title).findUnique();
      return ok(post.render(post_));
   }


   /**
    * Display the 'new post form'.
    */
   @Security.Authenticated(Secured.class)
   public static Result create() {
      Form<Post> newPostForm = form(Post.class);
      return ok(createpost.render(newPostForm));
   }

   /**
    * Handle the 'new post form' submission
    */
   @Security.Authenticated(Secured.class)
   public static Result save() {
      Form<Post> newPostForm = form(Post.class).bindFromRequest();
      if (newPostForm.hasErrors()) {
         return badRequest(createpost.render(newPostForm));
      }
      Post post = newPostForm.get();
      //   post.setHtmlPreview(.TextUtils.generateTruncateHtmlPreview(blogPost.getBody(), TRUNCATED_MEDIUM_CHAR_COUNT));
      //TODO local user!
      User localUser = Application.getLocalUser(session());
      post.setUser(localUser);
      post.setCreationDate(new Date());

      post.save();
      flash("success", "Post has been created");
      return redirect(controllers.routes.Application.admin());
   }

   /**
    * Display the 'edit form' of a existing Post.
    *
    * @param postId Id of the blog post to edit
    */
   @Security.Authenticated(Secured.class)
   public static Result edit(UUID postId) {
      Form<Post> postForm = form(Post.class).fill(Post.find.byId(postId));
      return ok(editpost.render(postId, postForm));
   }


   /**
    * Handle the 'edit form' submission
    *
    * @param postId Id of the blog post to edit
    */
   @Security.Authenticated(Secured.class)
   public static Result doEdit(UUID postId) {
      Form<Post> postForm = form(Post.class).bindFromRequest();
      if (postForm.hasErrors()) {
         return badRequest(editpost.render(postId, postForm));
      }
      Post postFromForm = postForm.get();
      //TODO info about blog
      //TODO merge/update functionality
      Post toUpdate = Post.find.byId(postId);
      toUpdate.setContent(postFromForm.getContent());
      toUpdate.setTitle(postFromForm.getTitle());
//      toUpdate.setName(postFromForm.getName());
//      toUpdate.setHtmlPreview(util.TextUtils.generateTruncateHtmlPreview(toUpdate.getBody(), TRUNCATED_MEDIUM_CHAR_COUNT));
      toUpdate.save();
      flash("success", "Post has been updated");
      /**
       * you can use redirect(routes.Application.viewPost(blogPost.save().getRid().toString()));
       * and if you use @With(LocalUser.class) it preferred way.
       * Only one plus of my solution - avoiding Post.findById query TODO only on create
       */
      return redirect(routes.Application.admin());
   }

}
