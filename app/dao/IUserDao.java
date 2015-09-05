package dao;

import com.orientechnologies.orient.core.record.impl.ODocument;
import models.user.User;

/**
 * Created by dru on 03.09.15.
 */
public interface IUserDao {

   User findByEmail(String email);

   User findByEmailAndPassword(String email, String password);

   boolean exist(String email, String password);

   void save(User user);
}
