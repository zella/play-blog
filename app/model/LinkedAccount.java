package model;


import com.feth.play.module.pa.user.AuthUser;

public class LinkedAccount   {


	private String providerUserId;
	private String providerKey;

    public static LinkedAccount create(final AuthUser authUser) {
        final LinkedAccount ret = new LinkedAccount();
        ret.update(authUser);
        return ret;
    }

    public void update(final AuthUser authUser) {
        this.providerKey = authUser.getProvider();
        this.providerUserId = authUser.getId();
    }
}