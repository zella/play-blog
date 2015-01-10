package model;


import javax.persistence.Id;

/**
 * Created by dru on 09.01.2015.
 */

public class User {

    @Id
    private Object rid;

    private String email;

    private String password;

    private boolean isAdmin;//TODO roles

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Object getRid() {
        return rid;
    }
}
