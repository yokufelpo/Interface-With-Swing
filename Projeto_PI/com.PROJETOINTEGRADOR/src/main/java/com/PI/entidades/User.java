/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.entidades;

/**
 *
 * @author Usuario
 */
//Class model
public class User {

    private int id;
    private String userName;
    private String password;

    public User(int id, String user) {
        this.id = id;
        this.userName = user;
    }

    public User(String user, String password) {
        this.userName = user;
        this.password = password;
    }
    
    public User(int id) {
        this.id = id;
    }

    public User() {}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

