package dao.impl;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import dao.IBlogPostDao;
import database.DB;
import models.Post;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dru on 03.09.15.
 */
public class OrientBlogPostDao implements IBlogPostDao {

   @Override
   public List<Post> findAll() {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         return db.query(
             new OSQLSynchQuery<ODocument>("select * from BlogPost")).stream()
             .map(doc -> Post.fromDocument((ODocument) doc))
             .collect(Collectors.toList());
      }
   }

   @Override
   public Post findById(String id) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         return Post.fromDocument(db.load(new ORecordId(id)));
      }
   }

   @Override
   public Post findByTitle(String title) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from BlogPost where title = ?");
         List<ODocument> result = db.command(query).execute(title);
         if (result.size() < 1)
            return null;
         else
            return Post.fromDocument(result.get(0));
      }
   }

   @Override
   public boolean isPostTitleUnique(String title) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         return findByTitle(title) == null;
      }
   }

   @Override
   public long count(boolean withPrivatePosts) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         String queryString;
         if (withPrivatePosts)
            queryString = "SELECT COUNT(*) as count FROM BlogPost";
         else
            queryString = "SELECT COUNT(*) as count FROM BlogPost WHERE isPrivate = false";

         List<ODocument> count = db.query(new OSQLSynchQuery<ODocument>(queryString));
         return count.get(0).field("count");
      }
   }

   @Override
   public List<Post> page(int pageIndex, int pageSize, boolean withPrivatePosts) {
      try (ODatabaseDocumentTx db = DB.acquire()) {


         String queryString;
         if (withPrivatePosts)
            queryString = "SELECT * FROM BlogPost skip ? limit ?";
         else
            queryString = "SELECT * FROM BlogPost WHERE isPrivate = false skip ? limit ?";

         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>(queryString);

         List<ODocument> result = db.command(query).execute(pageSize * (pageIndex - 1), pageSize);
         return result.stream()
             .map(doc -> Post.fromDocument((ODocument) doc))
             .collect(Collectors.toList());
      }
   }

   @Override
   public void save(Post post) {
      //TODO validation user exist, maybe aspectj
      try (ODatabaseDocumentTx db = DB.acquire()) {
         ODocument doc = post.toDocument();
         db.save(doc);
      }
   }

   @Override
   public void delete(Post post) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         ODocument doc = post.toDocument();
         db.delete(doc);
      }
   }


}
