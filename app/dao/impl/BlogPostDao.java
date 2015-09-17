package dao.impl;

import models.Post;

import java.util.List;
import java.util.UUID;

/**
 * Created by dru on 02.09.15.
 */
@Deprecated
public class BlogPostDao  {

//   @Override
//   public List<Post> findAll() {
//      //TODO make stricted version, where fetches titles only
//      return Post.finder.order().desc("creationDate").findList();
//   }
//
//   @Override
//   public boolean isPostTitleUnique(String title) {
//      return Post.finder.query()
//          .where().eq("title", title).findUnique() == null;
//   }
//
//   @Override
//   public Post findById(String id) {
//      return Post.finder.byId(UUID.fromString(id));
//   }
//
//   @Override
//   public Post findByTitle(String title) {
//      return Post.finder.query()
//          .where().eq("title", title).findUnique();
//   }
//
//   @Override
//   public int count(boolean showPrivates) {
//      if (showPrivates)
//         return Post.finder.findRowCount();
//      else
//         return Post.finder.query()
//             .where().eq("isPrivate", false).findRowCount();
//   }
//
//   /**
//    * First page - 1
//    *
//    * @param pageIndex
//    * @param pageSize
//    * @param showPrivates
//    * @return
//    */
//   @Override
//   public List<Post> page(int pageIndex, int pageSize, boolean showPrivates) {
//      List<Post> postsOnPage;
//      if (showPrivates)
//         postsOnPage = Post.finder
//             .query()
//             .order().desc("creationDate")
//             .findPagedList(pageIndex - 1, pageSize)
//             .getList();
//      else
//         postsOnPage = Post.finder
//             .query()
//             .where().eq("isPrivate", false)
//             .order().desc("creationDate")
//             .findPagedList(pageIndex - 1, pageSize)
//             .getList();
//
//      return postsOnPage;
//   }

}
