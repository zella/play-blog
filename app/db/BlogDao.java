package db;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import model.Comment;
import model.Post;
import model.User;

import java.util.ArrayList;
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

    public static Post getPostById(String id) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            //TODO typing?
            return db.load(new ORecordId(id));
        }
    }

    public static User saveNewUser(User user) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            return db.save(user);
        }
    }

    public static void addComment(Comment comment, Post source) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            source.getComments().add(comment);
            db.save(source);
        }
    }

    public static User findUserByEmail(String email) {
        try (OObjectDatabaseTx db = acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from User where email = ?");
            List<User> users = db.command(query).execute(email);
            if (users.isEmpty()) return null;
            else
                return users.get(0);

        }
    }


}
