package controllers;

import model.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(final Context ctx) {
        final AuthUser authUser = PlayAuthenticate.getUser(ctx.session());

        if (authUser != null) {
            User user = User.findByAuthUserIdentity(authUser);
            ctx.args.put("user", user);
            return user.getRid().toString();
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(final Context ctx) {
        ctx.flash().put(Application.FLASH_MESSAGE_KEY, "Nice try, but you need to log in first!");
        //auth protect from commenting, also from admining
        return unauthorized("need login");
    }
}