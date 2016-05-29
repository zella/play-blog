package config;


import play.Play;

public class Config {

   private static Config instance;

   private final String dbUrl;
   private final String dbUser;
   private final String dbPass;

   private final String disqusId;

   private Config() {
      dbUrl = Play.application().configuration().getString("orient.db_url");
      dbUser = Play.application().configuration().getString("orient.db_user");
      dbPass = Play.application().configuration().getString("orient.db_pass");
      disqusId = Play.application().configuration().getString("disqus.id");
   }

   public static Config getInstance() {
      if (instance == null) {
         instance = new Config();
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

   public String getDisqusId() {
      return disqusId;
   }
}
