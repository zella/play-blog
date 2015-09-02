package controllers;

import com.avaje.ebean.Ebean;
import dao.BlogPostDao;
import models.Post;
import models.user.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import utils.TextUtils;
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

      Post post_ = BlogPostDao.findByTitle(title);

      if (post_ == null) {
         return notFound("Page not found");
      }

      //TODO multiuser
//
//      if (post_.getIsPrivate()) {
//         User localUser = Application.getLocalUser(session());
//         //don't allow see private post from other users
//         if (localUser != null && post_.getUser().equals(localUser))
//            return ok(post.render(post_));
//         else
//            return notFound("Page not found");
//      } else
//         return ok(post.render(post_));

      if (post_.getIsPrivate() && Application.getLocalUser(session()) == null) {
         return notFound("Page not found");
      } else
         return ok(post.render(post_, BlogPostDao.findAllWithTitlesOnly()));

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
      post.setHtmlPreview(TextUtils.generateTruncateHtmlPreview(post.getContent(), TextUtils.TRUNCATED_CHAR_COUNT));
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
      Form<Post> postForm = form(Post.class).fill(BlogPostDao.findById(postId.toString()));
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
      Post toUpdate = BlogPostDao.findById(postId.toString());
      toUpdate.setContent(postFromForm.getContent());
      toUpdate.setTitle(postFromForm.getTitle());
      toUpdate.setIsPrivate(postFromForm.getIsPrivate());
      toUpdate.setHtmlPreview(TextUtils.generateTruncateHtmlPreview(postFromForm.getContent(), TextUtils.TRUNCATED_CHAR_COUNT));
      toUpdate.save();
      flash("success", "Post has been updated");
      /**
       * you can use redirect(routes.Application.viewPost(blogPost.save().getRid().toString()));
       * and if you use @With(LocalUser.class) it preferred way.
       * Only one plus of my solution - avoiding Post.findById query TODO only on create
       */
      return redirect(routes.Application.admin());
   }


   /**
    * Handle delete post
    */
   @Security.Authenticated(Secured.class)
   public static Result delete(String id) {

//      Post toDelete = Post.find.byId(UUID.fromString(id));
//      if (toDelete != null) {
//         toDelete.delete();
//         flash("success", "Post has been deleted");
//      } else {
//         flash("error", "Error, post not exist");
//      }
//      return redirect(routes.Application.admin());

      Post toDelete = BlogPostDao.findById(id);
      if (toDelete != null) {
         toDelete.delete();
         flash("success", "Post has been deleted");
         return ok(id);
      } else {
         flash("error", "Error, post not exist");
         return forbidden(id);

      }

   }


}
