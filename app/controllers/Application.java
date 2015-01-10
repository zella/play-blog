package controllers;

import db.BlogDao;
import model.Comment;
import play.data.Form;
import play.mvc.*;

import views.html.*;


import static play.data.Form.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(BlogDao.getAllPosts()));
    }

    public static Result detailpost(String postId) {
        return ok(detailpost.render(BlogDao.getPostById(postId)));
    }

    public static Result addcomment(String postId) {
        Form<Comment> commentForm = form(Comment.class).bindFromRequest();
        Comment comment =commentForm.get();
        BlogDao.addComment(comment,BlogDao.getPostById(postId));
      //  return redirect(controllers.routes.Application.detailpost(postId));
        return ok("ok");
    }

}
