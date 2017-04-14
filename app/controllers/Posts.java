package controllers;

import models.Post;
import models.user.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.TextUtils;
import views.html.*;

import java.util.Date;

import static play.data.Form.form;

public class Posts extends Controller {


   public static Result detail(String id) {

      Post post_ = Application.postDao.findById(id);

      if (post_ == null) {
         return notFound("Page not found");
      }

      boolean isLogged = Application.getLocalUser(session()) != null;

      if (post_.getIsPrivate() && !isLogged) {
         return notFound("Page not found");
      } else
         return ok(post.render(post_, Application.postDao.findAll(isLogged)));

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

      User localUser = Application.getLocalUser(session());
      // now we store raw html in database, yeah
      post.setContent(TextUtils.markdownToHtml(post.getMdContent()));
      post.setHtmlPreview(TextUtils.markdownToHtml(post.getMdPreview()));
      post.setUser(localUser);
      post.setCreationDate(new Date());

      Application.postDao.save(post);

      flash("success", "Post has been created");
      return redirect(controllers.routes.Application.admin());
   }

   /**
    * Display the 'edit form' of a existing Post.
    *
    * @param postId Id of the blog post to edit
    */
   @Security.Authenticated(Secured.class)
   public static Result edit(String postId) {
      Form<Post> postForm = form(Post.class).fill(Application.postDao.findById(postId));
      return ok(editpost.render(postId, postForm));
   }


   /**
    * Handle the 'edit form' submission. Ajax action
    *
    * @param postId Id of the blog post to edit
    */
   @Security.Authenticated(Secured.class)
   public static Result doEdit(String postId) {
      Form<Post> postForm = form(Post.class).bindFromRequest();
      if (postForm.hasErrors()) {
         return badRequest(postForm.errorsAsJson().toString());
      }
      Post postFromForm = postForm.get();
      Post toUpdate = Application.postDao.findById(postId);
      // now we store raw html in database, yeah
      toUpdate.setContent(TextUtils.markdownToHtml(postFromForm.getMdContent()));
      toUpdate.setMdContent(postFromForm.getMdContent());
      toUpdate.setTitle(postFromForm.getTitle());
      toUpdate.setIsPrivate(postFromForm.getIsPrivate());
      toUpdate.setHtmlPreview(TextUtils.markdownToHtml(postFromForm.getMdPreview()));
      toUpdate.setMdContent(postFromForm.getMdContent());
      Application.postDao.save(toUpdate);

      return ok("Blog post saved");
   }

   /**
    * Handle delete post as ajax action
    */
   @Security.Authenticated(Secured.class)
   public static Result delete(String id) {
      Post toDelete = Application.postDao.findById(id);
      if (toDelete != null) {
         Application.postDao.delete(toDelete);
         flash("success", "Post has been deleted");
         return ok(id);
      } else {
         flash("error", "Error, post not exist");
         return forbidden(id);
      }

   }


}
