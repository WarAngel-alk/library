package com.my.bussiness.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 09.03.14
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private long id;
    private String name;
    private String passwordHash;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
