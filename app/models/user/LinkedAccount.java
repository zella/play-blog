package models.user;

import com.avaje.ebean.Model;
import com.feth.play.module.pa.user.AuthUser;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Several accounts support. Not using yet.
 *
 *
 *
 * See <a href="https://github.com/joscha/play-authenticate">https://github.com/joscha/play-authenticate</a>
 *
 */
@Entity
@Table(name = "linked_accounts")
public class LinkedAccount extends Model {

   @Id
   public Long id;

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