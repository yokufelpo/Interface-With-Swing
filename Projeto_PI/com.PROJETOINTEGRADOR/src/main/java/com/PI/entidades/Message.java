/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.entidades;

/**
 *
 * @author Usuario
 */
public class Message {
    private int id;
    private String message;
    private Contact cont;
    
    public Message(String message) {
        this.message = message;
    }
    
     public Message(int id) {
        this.id = id;
    }
     
    public Message() {}
    
    public Message(int id, Contact contName, String message) {
        this.id = id;
        this.cont = contName;
        this.message = message;
    }
    
    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setContId(Contact contName) {
        this.cont = contName;
    }
    
    public Contact getContId() {
        return cont;
    }
}




