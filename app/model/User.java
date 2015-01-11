package model;


import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import db.DB;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

/**
 * Created by dru on 09.01.2015.
 */

public class User {

    @Id
    private Object rid;

    private String name;

    private String email;

    @Embedded
    public List<LinkedAccount> linkedAccounts;

    private boolean isAdmin;//TODO roles

    public User() {
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Object getRid() {
        return rid;
    }


    public static User createAndSave(final AuthUser authUser) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            final User user = new User();
            user.linkedAccounts = Collections.singletonList(LinkedAccount
                    .create(authUser));

            if (authUser instanceof EmailIdentity) {
                final EmailIdentity identity = (EmailIdentity) authUser;
                // Remember, even when getting them from FB & Co., emails should be
                // verified within the application as a security breach there might
                // break your security as well!
                user.email = identity.getEmail();
            }

            if (authUser instanceof NameIdentity) {
                final NameIdentity identity = (NameIdentity) authUser;
                final String name = identity.getName();
                if (name != null) {
                    user.name = name;
                }
            }
            return db.save(user);
        }
    }

    public static User findByEmail(final String email) {
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from User where email = ?");
            List<User> users = db.command(query).execute(email);
            if (users.isEmpty()) return null;
            else
                return users.get(0);
        }
    }

    public static User findByAuthUserIdentity(final AuthUserIdentity authUserIdentity) {
        if (authUserIdentity == null) return null;
        try (OObjectDatabaseTx db = DB.acquireDatabase()) {
            OSQLSynchQuery query = new OSQLSynchQuery<User>("select from User where linkedAccounts contains (providerUserId = ? and providerKey = ?)");
            List<User> users = db.command(query).execute(authUserIdentity.getId(), authUserIdentity.getProvider());
            if (users.isEmpty()) return null;
            else
                return users.get(0);
        }
    }

    public static boolean existsByAuthUserIdentity(final AuthUserIdentity identity) {
        return findByAuthUserIdentity(identity) != null;
    }
}
