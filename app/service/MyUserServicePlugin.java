package service;

import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import db.BlogDao;
import model.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

import java.util.Collections;

public class MyUserServicePlugin extends UserServicePlugin {

    public MyUserServicePlugin(final Application app) {
        super(app);
    }

    @Override
    public Object save(final AuthUser authUser) {
        System.out.println("save_user");
        System.out.println(authUser.toString());
        final User user = new User();

        if (authUser instanceof EmailIdentity) {
            final EmailIdentity identity = (EmailIdentity) authUser;
            // Remember, even when getting them from FB & Co., emails should be
            // verified within the application as a security breach there might
            // break your security as well!
            user.setEmail(identity.getEmail());
        }

        if (authUser instanceof NameIdentity) {
            final NameIdentity identity = (NameIdentity) authUser;
            final String name = identity.getName();
            if (name != null) {
                user.setName(name);
            }
        }
        return BlogDao.saveNewUser(user).getRid().toString();
    }

    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {
        System.out.println("local_identity");
        // For production: Caching might be a good idea here...
        // ...and dont forget to sync the cache when users get deactivated/deleted
        if (identity instanceof EmailIdentity) {
            final EmailIdentity emailIdentity = (EmailIdentity) identity;
            final User user = BlogDao.findUserByEmail(emailIdentity.getEmail());
            if (user != null) return user.getRid().toString();
        }
        return null;
    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
        System.out.println("merge");
        return oldUser;
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
        System.out.println("link");
        //TODO it's ok?
        return null;
    }

}
