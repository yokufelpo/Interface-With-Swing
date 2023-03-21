/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.database;

import com.PI.entidades.Contact;
import com.PI.entidades.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Usuario
 */
public class ContactDao {
    private final DatabaseConnection databaseConnection;
    
    public ContactDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    //cadastro contato
    public int save(Contact contact) throws SQLException {
        String sql = "INSERT INTO contact (contactname, id_current, id_contact) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, contact.getContactName());
        preparedStatement.setInt(2, contact.getCurrentId().getId()); 
        preparedStatement.setInt(3, contact.getContactId().getId());     

        return preparedStatement.executeUpdate();
    }
    // conferir se contato existe
    public ResultSet first(Contact currentId, Contact contactId) throws SQLException {
        String sql = 
            "SELECT contactname, id_contact, id_current FROM contact WHERE id_current = ? AND id_contact = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);       
        preparedStatement.setInt(1, currentId.getCurrentId().getId());
        preparedStatement.setInt(2, contactId.getContactId().getId());
        ResultSet rs = preparedStatement.executeQuery();
        return rs;  
    }
    // listar contatos
    public Vector<Contact> findAll(User currentId) throws SQLException {
        String sql = 
            "SELECT * FROM contact WHERE id_current = ? ORDER BY contactname";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, currentId.getId());
        ResultSet rs = preparedStatement.executeQuery();
        Vector<Contact> contacts = new Vector<>();
        while (rs.next()) {  
            contacts.add(new Contact(
                rs.getInt("id"), rs.getString("contactname"), new User(rs.getInt("id_contact"))
            ));          
        }
        return contacts;       
    }
    //mudar nome 
    public int update(Contact contact) throws SQLException {
        String sql = "UPDATE contact SET contactname = ? WHERE id = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, contact.getContactName());
        preparedStatement.setInt(2, contact.getId());

        return preparedStatement.executeUpdate();
    }    
    //excluir contato
    public int delete(Contact contact) throws SQLException {
        String sql = "DELETE FROM contact WHERE id = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, contact.getId());
        return preparedStatement.executeUpdate();
    }
}
