package model;

import javax.persistence.Entity;

/**
 * Created by dru on 09.01.2015.
 */
@Entity
public class User {

    private String email;

    private String password;

    private boolean isAdmin;
}
