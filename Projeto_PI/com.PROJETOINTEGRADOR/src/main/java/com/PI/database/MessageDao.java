/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.database;

import com.PI.entidades.Contact;
import com.PI.entidades.Message;
import com.PI.entidades.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Usuario
 */
public class MessageDao {
    private final DatabaseConnection databaseConnection;
    
    public MessageDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    //cadastro contato
    public int save(Message message) throws SQLException {
        String sql = "INSERT INTO messages (message, id_cont) VALUES (?, ?)";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, message.getMessage());
        preparedStatement.setInt(2, message.getContId().getId()); 

        return preparedStatement.executeUpdate();
    }
    // listar mensagens enviadas
    public Vector<Message> findAll(User currentId) throws SQLException {
        String sql = 
            """
            SELECT m.id, c.contactname, m.message, m.id_cont 
            FROM messages m 
            INNER JOIN contact c 
            ON c.id = m.id_cont
            WHERE c.id_current = ?
            ORDER BY c.contactname, m.message
            """;
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, currentId.getId());
        ResultSet rs = preparedStatement.executeQuery();
        Vector<Message> messages = new Vector<>();
       
        while (rs.next()) {  
            messages.add(new Message(
                rs.getInt("m.id"), new Contact(rs.getString("c.contactname")), rs.getString("m.message"))
            );          
        }
        return messages;       
    }
    // listar mensagens recebidas - id contact é o current user
    public Vector<Message> findAll(Contact contactd) throws SQLException {
        String sql = 
            """
            SELECT m.id, m.message, m.id_cont, c.id_current 
            FROM messages m 
            INNER JOIN contact c 
            ON c.id = m.id_cont
            WHERE c.id_contact = ?
            ORDER BY c.contactname, m.message
            """;
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, contactd.getContactId().getId());       
        ResultSet rs = preparedStatement.executeQuery();
        Vector<Message> messages = new Vector<>();
        while (rs.next()) {  
            messages.add(new Message(
                rs.getInt("m.id"), new Contact(rs.getInt("m.id_cont"), new User(rs.getInt("c.id_current"))), 
                rs.getString("m.message"))
            );          
        }
        return messages;       
    }
 
    //editar mensagem 
    public int update(Message message) throws SQLException {
        String sql = "UPDATE messages SET message = ? WHERE id = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, message.getMessage());
        preparedStatement.setInt(2, message.getId());

        return preparedStatement.executeUpdate();
    }    
    //excluir mensagem
    public int delete(Message message) throws SQLException {
        String sql = "DELETE FROM messages WHERE id = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, message.getId());
        return preparedStatement.executeUpdate();
    }
}
