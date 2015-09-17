package database;


import play.Play;

public class OConfig {

   private static OConfig instance;

   private String dbUrl;
   private String dbUser;
   private String dbPass;

   private OConfig() {
      dbUrl = Play.application().configuration().getString("orient.db_url");
      dbUser = Play.application().configuration().getString("orient.db_user");
      dbPass = Play.application().configuration().getString("orient.db_pass");
   }

   public static OConfig getInstance() {
      if (instance == null) {
         instance = new OConfig();
      }
      return instance;
   }

   public String getDbUrl() {
      return dbUrl;
   }

   public String getDbUser() {
      return dbUser;
   }

   public String getDbPass() {
      return dbPass;
   }
}
