import model.Blog;
import model.Post;
import model.User;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created by dru on 10.01.2015.
 */
public class Global extends GlobalSettings {


    @Override
    public void onStart(Application application) {
        super.onStart(application);

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

        Formatters.register(User.class, new Formatters.SimpleFormatter<User>() {
            @Override
            public User parse(String id, Locale locale) throws ParseException {
                return User.findById(id);
            }

            @Override
            public String print(User user, Locale locale) {
                return user.getRid().toString();
            }
        });
        Formatters.register(Blog.class, new Formatters.SimpleFormatter<Blog>() {
            @Override
            public Blog parse(String name, Locale locale) throws ParseException {
                return Blog.findByName(name);
            }

            @Override
            public String print(Blog blog, Locale locale) {
                return blog.getName();
            }
        });
    }


}
