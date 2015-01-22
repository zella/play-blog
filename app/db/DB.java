package db;

import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import model.*;

/**
 * Created by dru on 10.01.2015.
 */
public class DB {

    protected final static String dbUrl = OConfig.getInstance().getDbUrl();
    protected final static String dbUser = OConfig.getInstance().getDbUser();
    protected final static String dbPassword = OConfig.getInstance().getDbPass();

    public static OObjectDatabaseTx acquireDatabase() {
        OObjectDatabaseTx database = OObjectDatabasePool.global().acquire(dbUrl, dbUser, dbPassword);
        database.getEntityManager().registerEntityClass(Post.class);
        database.getEntityManager().registerEntityClass(User.class);
        database.getEntityManager().registerEntityClass(Comment.class);
        database.getEntityManager().registerEntityClass(LinkedAccount.class);
        database.getEntityManager().registerEntityClass(Blog.class);
        database.setLazyLoading(false);//I have issue with javasisst and java8
        return database;
    }
}
