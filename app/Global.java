import model.Post;
import model.User;
import play.Application;
import play.GlobalSettings;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;

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

        PlayAuthenticate.setResolver(new PlayAuthenticate.Resolver() {

            @Override
            public Call login() {
                // Your login page
                return routes.Application.index();
            }

            @Override
            public Call afterAuth() {
                // The user will be redirected to this page after authentication
                // if no original URL was saved
                return routes.Application.index();
            }

            @Override
            public Call afterLogout() {
                return routes.Application.index();
            }

            @Override
            public Call auth(final String provider) {
                // You can provide your own authentication implementation,
                // however the default should be sufficient for most cases
                return com.feth.play.module.pa.controllers.routes.Authenticate
                        .authenticate(provider);
            }

            @Override
            public Call onException(final AuthException e) {
                if (e instanceof AccessDeniedException) {
                    return routes.Application
                            .oAuthDenied(((AccessDeniedException) e)
                                    .getProviderKey());
                }

                // more custom problem handling here...

                return super.onException(e);
            }

            @Override
            public Call askLink() {
                // We don't support moderated account linking in this sample.
                // See the play-authenticate-usage project for an example
                return null;
            }

            @Override
            public Call askMerge() {
                // We don't support moderated account merging in this sample.
                // See the play-authenticate-usage project for an example
                return null;
            }
        });
    }


}
