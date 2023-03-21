/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.database;

import com.PI.entidades.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Usuario
 */
public class UserDao {
    private final DatabaseConnection databaseConnection;

    public UserDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    //Cadastrar
    public int save(User user) throws SQLException {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        
        return preparedStatement.executeUpdate();
    }
    //Logar
    public ResultSet login(String userName, String password) throws SQLException {
        String sql = 
            "SELECT * FROM user WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;  
    }
    //Listar usuarios
    public Vector<User> findAll() throws SQLException {
        String sql = "SELECT id, username FROM user ORDER BY id DESC";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        Vector<User> users = new Vector<>();
        while (rs.next()) {
            users.add(new User(
                rs.getInt("id"), rs.getString("username")));
        }
        return users;
    }
    //Excluir current user
    public int delete(User user) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        return preparedStatement.executeUpdate();
    }
}
