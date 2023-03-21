/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class DatabaseConnection {
    private Connection connection;

    private final String baseUrl = "jdbc:mysql://";
    private final String user;
    private final String password;
    private final String database;
    private final String server;
    private final int port;

    public DatabaseConnection(
        String user, String password, String database, String server, int port
    ) throws SQLException {
            this.user = user;
            this.password = password;
            this.database = database;
            this.server = server;
            this.port = port;

            StringBuilder urlTemp = new StringBuilder(baseUrl);
            urlTemp.append(server).append(":").append(port).append("/").append(database);

            this.connection = (Connection) DriverManager.getConnection(urlTemp.toString(), user, password);      
    }
    public Connection getConnection() {
        return connection;
    }
    
}
