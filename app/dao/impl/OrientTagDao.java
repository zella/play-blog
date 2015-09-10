package dao.impl;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import dao.ITagDao;
import database.DB;
import models.Tag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dru on 10.09.15.
 */
public class OrientTagDao implements ITagDao {

   @Override
   public List<Tag> findStartWithIgnoreCase(String pattern) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         //example "vasya" = "'vasya%'"
         String queryPattern = String.format("'%s%%'", pattern.toLowerCase());

         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from PostTag where name LIKE ?");
         List<ODocument> result = db.command(query).execute(queryPattern);
         return result.stream().map(doc -> Tag.fromDocument(doc)).collect(Collectors.toList());
      }

   }

   @Override
   public boolean existIgnoreCase(String name) {

      try (ODatabaseDocumentTx db = DB.acquire()) {
         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from PostTag where name = ?");
         List<ODocument> result = db.command(query).execute(name.toLowerCase());
         return (result.size() > 0);
      }

   }

   @Override
   public void save(Tag tag) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         ODocument doc = tag.toDocument();
         db.save(doc);

      }
   }

   @Override
   public void delete(Tag tag) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         ODocument doc = tag.toDocument();
         db.delete(doc);
      }
   }
}
