import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import db.BlogDao;
import model.Post;
import model.User;
import play.Application;
import play.GlobalSettings;

import java.util.List;

/**
 * Created by dru on 10.01.2015.
 */
public class Global extends GlobalSettings {


    @Override
    public void onStart(Application application) {
        super.onStart(application);


//        //initial data, for testing
        User user1 = new User("mama1", "pass");
        User user2 = new User("mama2", "pass");
//      //  User user3 = new User("mama3", "pass");
//     //   User user4 = new User("mama4", "pass");
//
        Post post1 = new Post("la lala lal alal","how mama ziggen",user1);
        Post post2 = new Post("opa opa opa ","how papa schlachen",user2);
//     //   Post post3 = new Post("body3","header",user3);
//      //  Post post4 = new Post("body4","header",user4);
//
//     //   BlogDao.saveNewUser(user2);
////        BlogDao.saveNewPost(post1);
//
//        BlogDao.saveNewPost(post1);
//        BlogDao.saveNewPost(post2);
    }


}
