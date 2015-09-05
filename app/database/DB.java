package database;

/**
 * Created by dru on 21.08.15.
 */


import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Created by dru on 10.01.2015.
 */
//TODO inject
public class DB {

   protected final static String sDbUrl = OConfig.getInstance().getDbUrl();
   protected final static String sDbUser = OConfig.getInstance().getDbUser();
   protected final static String sDbPassword = OConfig.getInstance().getDbPass();

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
         System.out.println("created");

         try {
            db = new ODatabaseDocumentTx(sDbUrl).open("admin", "admin");
            System.out.println("opened");
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

}
