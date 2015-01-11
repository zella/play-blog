package model;


import javax.persistence.Id;

/**
 * Created by dru on 09.01.2015.
 */

public class User {

    @Id
    private Object rid;

    private String name;

    private String email;

    private String password;

    private String googleUserId;

    private boolean isAdmin;//TODO roles

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public Object getRid() {
        return rid;
    }
}
