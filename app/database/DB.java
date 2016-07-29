package database;

/**
 * Created by dru on 21.08.15.
 */


import com.orientechnologies.orient.core.command.OCommandOutputListener;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.tool.ODatabaseImport;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import config.Config;
import play.Logger;
import utils.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dru on 10.01.2015.
 */
//TODO inject
public class DB {

   protected final static String sDbUrl = Config.getInstance().getDbUrl();
   protected final static String sDbUser = Config.getInstance().getDbUser();
   protected final static String sDbPassword = Config.getInstance().getDbPass();

   private static OPartitionedDatabasePoolFactory poolFactory = new OPartitionedDatabasePoolFactory();

   public static ODatabaseDocumentTx acquire() {
    //  return ODatabaseDocumentPool.global().acquire(sDbUrl, sDbUser, sDbPassword);
      //  return new ODatabaseDocumentTx(sDbUrl).open(sDbUser, sDbPassword);
      return poolFactory.get(sDbUrl, sDbUser, sDbPassword).acquire();
   }

   public static void createIfNotExist() {
      ODatabaseDocumentTx db = new ODatabaseDocumentTx(sDbUrl);
      if (!db.exists()) {
         db.create();
         Logger.info("db created");

         try {
            db = new ODatabaseDocumentTx(sDbUrl).open("admin", "admin");
            Logger.info("db opened");
            //update current admin user from from config
            ODocument admin = db.getUser().getDocument();
            admin.field("name", sDbUser);
            admin.field("password", sDbPassword);
            admin.save();

            db.getMetadata().getSchema().createClass("User");
            db.getMetadata().getSchema().createClass("BlogPost");

         } finally {
            db.close();
         }
      }
   }

   /**
    * @param file
    * @return success
    */
   public static boolean importBlog(File file) {
      try (ODatabaseDocumentTx db = DB.acquire()) {

         ODatabaseImport imp = new ODatabaseImport(db, file.getAbsolutePath(), new OCommandOutputListener() {
            @Override
            public void onMessage(String iText) {
               Logger.info("Importing database: {}", iText);
            }
         });
         imp.importDatabase();
         checkAndRepairPosts();

      } catch (IOException e) {
         Logger.error("Error import database", e);
         return false;
      }


      return true;
   }


   /**
    * Repair posts, for backward compatibility
    */
   public static void checkAndRepairPosts() {
      try (ODatabaseDocumentTx db = DB.acquire()) {
         List<ODocument> posts = db.query(new OSQLSynchQuery<ODocument>("select from BlogPost"));
         posts.forEach(DB::tryRepairPost);
      }
   }

   /**
    * @param doc
    * @return was repaired
    */
   private static boolean tryRepairPost(ODocument doc) {
      //0.1-SNAPSHOT -> 0.2-SNAPSHOT
      if (!doc.containsField("mdContent")) {

         String mdContent = doc.field("content");
         doc.field("mdContent", mdContent);
         String content = TextUtils.markdownToHtml(mdContent);
         doc.field("content", content);
         doc.field("htmlPreview", TextUtils.generateTruncatedHtmlPreview(content));
         Logger.info("Post {} was repaired", doc.getIdentity().toString());
         doc.save();
         return true;
      }
       Logger.info("Post {} ok, not repaired", doc.getIdentity().toString());
      return false;
   }


}
