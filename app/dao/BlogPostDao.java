package dao;

import models.Page;
import models.Post;

import java.util.List;
import java.util.UUID;

/**
 * Created by dru on 02.09.15.
 */
public class BlogPostDao {

   public static List<Post> findAllWithTitlesOnly() {
      //TODO fetch titles only
      return Post.finder.order().desc("creationDate").findList();
   }

   public static boolean isPostTitleUnique(String title) {
      return Post.finder.query()
          .where().eq("title", title).findUnique() == null;
   }

   public static Post findById(String id) {
      return Post.finder.byId(UUID.fromString(id));
   }

   public static Post findByTitle(String title) {
      return Post.finder.query()
          .where().eq("title", title).findUnique();
   }

   public static int count(boolean showPrivates) {
      if (showPrivates)
         return Post.finder.findRowCount();
      else
         return Post.finder.query()
             .where().eq("isPrivate", false).findRowCount();
   }

   /**
    * First page - 1
    *
    * @param pageIndex
    * @param pageSize
    * @param showPrivates
    * @return
    */
   public static List<Post> page(int pageIndex, int pageSize, boolean showPrivates) {
      List<Post> postsOnPage;
      if (showPrivates)
         postsOnPage = Post.finder
             .query()
             .order().desc("creationDate")
             .findPagedList(pageIndex - 1, pageSize)
             .getList();
      else
         postsOnPage = Post.finder
             .query()
             .where().eq("isPrivate", false)
             .order().desc("creationDate")
             .findPagedList(pageIndex - 1, pageSize)
             .getList();

      return postsOnPage;
   }

}
