package service;

import model.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

public class MyUserServicePlugin extends UserServicePlugin {

    public MyUserServicePlugin(final Application app) {
        super(app);
    }

    @Override
    public Object save(final AuthUser authUser) {
        System.out.println("save_user");
        if (User.existsByAuthUserIdentity(authUser)) return null;
        return User.createAndSave(authUser).getRid().toString();
    }

    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {
        System.out.println("local_identity");
        final User user = User.findByAuthUserIdentity(identity);
        if (user != null)
            return user.getRid().toString();
        else
            return null;

    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
        return oldUser;
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
        //Not supported yet
        return null;
    }

}
