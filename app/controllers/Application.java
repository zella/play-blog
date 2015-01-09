package controllers;

import play.*;
import play.api.libs.Collections;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Application extends Controller {

    public static Result index() {

        Set<Object> set = new TreeSet<Object>((o1, o2) -> 0);
        ArrayList<Object> list = new ArrayList<>();
        java.util.Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
      //  set.
        return ok(index.render("Your new application is ready."));
    }

}
