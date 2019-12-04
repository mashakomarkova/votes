package com.komarkova.voteSystem.db.entity;


import java.sql.Blob;

public class User extends Entity {


    private String login;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Blob userPicture;
    private Integer roleId;

    public Blob getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(Blob userPicture) {
        this.userPicture = userPicture;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getId().toString();
    }
}
