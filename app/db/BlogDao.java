package db;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import model.Post;
import model.User;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dru on 10.01.2015.
 */
public class BlogDao extends OAbstractDao {

    public static void saveNewPost(Post post) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            db.save(post);
        }
    }

    public static List<Post> getAllPosts() {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            //TODO or sql query - test performance
            Iterator<Post> it = db.browseClass(Post.class);
            List<Post> posts = Lists.newArrayList(it);
            return posts;
        }
    }

    public static void saveNewUser(User user) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            db.save(user);
        }
    }


}
