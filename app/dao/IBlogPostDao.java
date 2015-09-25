package dao;

import models.Post;

import java.util.List;

/**
 * Created by dru on 03.09.15.
 */
public interface IBlogPostDao {

   List<Post> findAll(boolean showPrivates);

   boolean isPostTitleUnique(String title);

   Post findById(String id);

   Post findByTitle(String title);

   long count(boolean withPrivatePosts);

   List<Post> page(int pageIndex, int pageSize, boolean showPrivates);

   void save(Post post);

   void delete(Post post);
}
