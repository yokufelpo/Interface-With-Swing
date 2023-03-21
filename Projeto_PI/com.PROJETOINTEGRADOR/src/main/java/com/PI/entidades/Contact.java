/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.entidades;

import java.util.Optional;

/**
 *
 * @author Usuario
 */
public class Contact {
    private int id;
    private String contactName;
    private Optional contactNameOp;
    private User currentId;
    private User contactId;
    private Optional contactIdOp;
    private Optional currentIdOp;
    
    public Contact(User currentId) {
        this.currentId = currentId;
    }
    
    public Contact(String contactName) {
        this.contactName = contactName;
    }
    
    public Contact(User currentId, String contactName) {
        this.contactName = contactName;
        this.currentId = currentId;
    }
    
    public Contact(int id) {
        this.id = id;
    }
    
    public Contact(int id, User currentId) {
        this.id = id;
        this.currentId = currentId;
    }
    
    public Contact(User currentId, User contactId) {
        this.currentId = currentId;
        this.contactId = contactId;      
    }
    
    public Contact(String contactName, User currentId, User contactId) {
        this.contactName = contactName;
        this.currentId = currentId;
        this.contactId = contactId;
    }
    //table
    public Contact(int id, String contactName, User contactId) { 
        this.id = id;
        this.contactId = contactId;
        this.contactName = contactName;
    }
    
     public Contact(String contactName, User contactId) { 
        this.contactId = contactId;
        this.contactName = contactName;
    }
    
    public Contact() {}
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public void setContactNameOp(Optional contactNameOp) {
        this.contactNameOp = contactNameOp;
    }
    
     public Optional getContactNameOp() {
        return contactNameOp;
    }
     
    public void setCurrentId(User currentId) {
        this.currentId = currentId;
    }
    
    public User getCurrentId() {
        return currentId;
    }
  
    public void setContactId(User contactId) {
        this.contactId = contactId;
    }
    
    public User getContactId() {
        return contactId;
    } 
    
    public void setCurrentIdOp(Optional currentIdOp) {
        this.currentIdOp = currentIdOp;
    }
    
    public Optional getCurrentIdOp() {
        return currentIdOp;
    } 
    
     public void setContactIdOp(Optional contactIdOp) {
        this.contactIdOp = contactIdOp;
    }
    
    public Optional getContactIdOp() {
        return contactIdOp;
    } 
    
}

