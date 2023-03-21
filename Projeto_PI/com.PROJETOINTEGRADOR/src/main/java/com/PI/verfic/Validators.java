/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.PI.verfic;

import PROJETO_INTEGRADOR.Senha;
import com.PI.database.ContactDao;
import com.PI.database.DatabaseConnection;
import com.PI.entidades.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Validators {
    
    public static boolean validateNotYou(Contact idContact) throws TreatmentException {
        if (idContact.getCurrentId().getId() == idContact.getContactId().getId()) {
            throw new TreatmentException("Não pode adicionar a si mesmo!");
        }
        return true;
    }
    
    public static boolean validateFirstCont(Contact idCurrent, Contact idContact) throws TreatmentException {
        try {           
            DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
            
            ContactDao contactDao = new ContactDao(databaseConnection);
            ResultSet rs = contactDao.first(idCurrent, idContact);
            if (rs.next()) {
                throw new TreatmentException(String.format(
                    "Contato já adicionado como '%s'", rs.getString("contactname").toUpperCase()));
            }         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }
}
