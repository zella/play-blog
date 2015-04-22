package model;


import util.TextUtils;

import java.util.Date;

/**
 * Created by dru on 09.01.2015.
 */

public class Comment {

    private String name;
    private String body;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReadableDate(){
        return TextUtils.toReadableDate(getDate());
    }
}
