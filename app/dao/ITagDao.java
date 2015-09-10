package dao;

import models.Tag;

import java.util.List;

/**
 * Created by dru on 03.09.15.
 */
public interface ITagDao {

   List<Tag> findStartWithIgnoreCase(String pattern);

   boolean existIgnoreCase(String name);

   void save(Tag tag);

   void delete(Tag tag);

}
