package dao.impl;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import dao.IUserDao;
import database.DB;
import models.Post;
import models.user.User;

import java.util.List;

/**
 * Created by dru on 03.09.15.
 */
public class OrientUserDao implements IUserDao {
   @Override
   public User findByEmail(String email) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from User where email = ?");
         List<ODocument> result = db.command(query).execute(email);
         if (result.size() < 1)
            return null;
         else
            return User.fromDocument(result.get(0));
      }
   }

   @Override
   public User findByEmailAndPassword(String email, String password) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<>("select * from User where email = ? and password = ?");
         List<ODocument> result = db.command(query).execute(email, password);
         if (result.size() < 1)
            return null;
         else
            return User.fromDocument(result.get(0));
      }
   }

   @Override
   public boolean exist(String email, String password) {
      return findByEmailAndPassword(email, password) != null;
   }

   @Override
   public void save(User user) {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         ODocument doc = user.toDocument();
         db.save(doc);
      }
   }
}
