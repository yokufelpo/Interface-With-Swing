/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.PI.inicial;

import PROJETO_INTEGRADOR.Senha;
import com.PI.database.ContactDao;
import com.PI.database.DatabaseConnection;
import com.PI.database.MessageDao;
import com.PI.database.UserDao;
import com.PI.entidades.User;
import comPI.contatos.AddContForm;
import com.PI.users.LoginForm;
import com.PI.entidades.Contact;
import com.PI.entidades.Message;
import com.PI.verfic.TreatmentException;
import com.PI.verfic.Validators;
import com.PI.messages.AddMesForm;
import com.PI.messages.SendboxForm;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Usuario
 */
public class Home extends javax.swing.JFrame {
    private static User currentUser;

    /** Creates new form Home
     * @param currentUser */
 
    public Home(User currentUser) {
        initComponents();
        Home.currentUser = currentUser;
        scrollUserTable.setVisible(false);
        deleteBtn.setVisible(false);
        messageArea.setEnabled(false);
    }
    public Home() {
        initComponents();
    }
    
    private void loadUserTable() throws SQLException {
        scrollUserTable.setVisible(true);
    
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        
        UserDao userDao = new UserDao(databaseConnection);
        Vector<User> users = userDao.findAll();
        DefaultTableModel userModel = (DefaultTableModel) userTable.getModel();
        for (User user : users) {
            String userName = user.getUserName();
            if (user.getId() == currentUser.getId()) {
                userName = currentUser.getUserName().concat("[você]"); 
            }
            userModel.addRow(new Object[]{
            user.getId(), userName 
            });             
        }
        deleteBtn.setVisible(true);
        loadBtn.setEnabled(false); 
        databaseConnection.getConnection().close();
    }
    
    private void deleteCurrentUser() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        
        UserDao userDao = new UserDao(databaseConnection);
        userDao.delete(currentUser);

        scrollUserTable.setVisible(false);
        deleteBtn.setVisible(false);
        addContBtn.setEnabled(false);
        JOptionPane.showMessageDialog(rootPane,
            "Usuário excluido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        databaseConnection.getConnection().close();
    }
    
    private void loadContTable() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        ContactDao contactDao = new ContactDao(databaseConnection);    
  
        Vector<Contact> contacts = contactDao.findAll(currentUser);
        DefaultTableModel contModel = (DefaultTableModel) contTable.getModel();
        for (Contact contact : contacts) {
            contModel.addRow(new Object[]{
                contact.getContactName(), contact.getContactId().getId(), contact.getId()
            });
        }   
        databaseConnection.getConnection().close();
    }
    
    public static Contact getContactInitForUpdate() {
        int rowSelected = contTable.getSelectedRow();        
        //using setters
        //contact.setContactName((String) contTable.getValueAt(rowSelected, 0));
        //contact.setContactId(new User((int) contTable.getValueAt(rowSelected, 1)));
        //using constructor
        return new Contact(
            (String) contTable.getValueAt(rowSelected, 0),
            new User((int) contTable.getValueAt(rowSelected, 1))
        );
    }
    public static int getContIdForUpdate() {
        return (int) contTable.getValueAt(contTable.getSelectedRow(), 2);
    }
    
    private void deleteContact() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        ContactDao contactDao = new ContactDao(databaseConnection);
       
        Contact contId = new Contact(getContIdForUpdate());
        contactDao.delete(contId);

        JOptionPane.showMessageDialog(rootPane,
            "Contato excluido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        databaseConnection.getConnection().close(); 
    }
    
    private void loadInboxTable() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        MessageDao messageDao = new MessageDao(databaseConnection);    
        
        Contact contactId = new Contact();
        contactId.setContactId(new User(currentUser.getId()));
        messageDao.findAll(contactId);
        
        Vector<Message> messages = messageDao.findAll(contactId);
        DefaultTableModel inboxModel = (DefaultTableModel) inboxTable.getModel();
        Integer[] idCurrents = new Integer[contTable.getRowCount()]; 
        String contactName = "[Desconhecido]"; 
        for (Message message : messages) {  
            for (int row=0; row < idCurrents.length; row++) {
                if (message.getContId().getCurrentId().getId() == (int) contTable.getValueAt(row, 1)){ 
                    contactName = (String) contTable.getValueAt(row, 0);  
                }
            }
            
            inboxModel.addRow(new Object[]{
               contactName, message.getMessage(), message.getId()
            });
        }  
        databaseConnection.getConnection().close();  
    }      
    
    public static void deleteMessage(JTable table) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "root", Senha.getPass(), "manager_messages", "localhost", 3306);
        MessageDao messageDao = new MessageDao(databaseConnection);
       
        Message msgId = new Message(getMsgIdForUpdate(table));
        messageDao.delete(msgId);

        JOptionPane.showMessageDialog(null,
            "Mensagem excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        databaseConnection.getConnection().close();  
    }
    public static int getMsgIdForUpdate(JTable table) {
        return (int) table.getValueAt(table.getSelectedRow(), 2);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMsgTab = new javax.swing.JTabbedPane();
        userPanel = new javax.swing.JPanel();
        scrollUserTable = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        loadBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        addContBtn = new javax.swing.JButton();
        addUserBtn = new javax.swing.JButton();
        contPanel = new javax.swing.JPanel();
        excContBtn = new javax.swing.JButton();
        editContBtn = new javax.swing.JButton();
        sendBtn = new javax.swing.JButton();
        scrollContTable = new javax.swing.JScrollPane();
        contTable = new javax.swing.JTable();
        reloadCBtn = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        scrollInboxTable = new javax.swing.JScrollPane();
        inboxTable = new javax.swing.JTable();
        readBtn = new javax.swing.JButton();
        responseBtn = new javax.swing.JButton();
        deleteInboxBtn = new javax.swing.JButton();
        sendedBtn = new javax.swing.JButton();
        scrollRead = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JTextArea();
        inboxLb = new javax.swing.JLabel();
        reloadIBtn = new javax.swing.JButton();
        addDirectPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manager Messages");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        addMsgTab.setBackground(new java.awt.Color(153, 255, 153));
        addMsgTab.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addMsgTab.setToolTipText("");
        addMsgTab.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        userPanel.setBackground(new java.awt.Color(204, 255, 204));
        userPanel.setPreferredSize(new java.awt.Dimension(600, 350));

        userTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuário"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.setAlignmentX(0.0F);
        userTable.setAlignmentY(0.0F);
        userTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        userTable.setGridColor(new java.awt.Color(0, 51, 153));
        userTable.setRowHeight(25);
        userTable.setSelectionBackground(new java.awt.Color(0, 102, 204));
        userTable.setShowVerticalLines(true);
        scrollUserTable.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            userTable.getColumnModel().getColumn(1).setPreferredWidth(115);
        }

        loadBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        loadBtn.setText("Carregar Usuários");
        loadBtn.setToolTipText("Carrega a lista com todos os usuários. Só pode ser feito uma vez.");
        loadBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 51, 51));
        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtn.setActionCommand("Excluir o Seu Usuário");
        deleteBtn.setLabel("Excluir o Seu Usuário");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        addContBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        addContBtn.setText("Adicionar Contato");
        addContBtn.setToolTipText("Adiciona um usuário  na sua lista de contatos.");
        addContBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addContBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContBtnActionPerformed(evt);
            }
        });

        addUserBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        addUserBtn.setText("Nova Sessão ");
        addUserBtn.setToolTipText("Abre uma nova janela de login. Atenção: ao fechar \nqualquer menu principal todas as sessões serão encerradas!");
        addUserBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollUserTable, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addContBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addUserBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(216, 216, 216)
                .addComponent(deleteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(loadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addContBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addUserBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 69, Short.MAX_VALUE))
                    .addComponent(scrollUserTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        addUserBtn.getAccessibleContext().setAccessibleDescription("Abre uma nova janela de login. ");

        addMsgTab.addTab("   Usuários   ", userPanel);

        contPanel.setBackground(new java.awt.Color(204, 255, 204));

        excContBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        excContBtn.setText("Excluir");
        excContBtn.setToolTipText("");
        excContBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        excContBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excContBtnActionPerformed(evt);
            }
        });

        editContBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        editContBtn.setText("Editar");
        editContBtn.setToolTipText("");
        editContBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        editContBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editContBtnActionPerformed(evt);
            }
        });

        sendBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        sendBtn.setText("Enviar Mensagem");
        sendBtn.setToolTipText("");
        sendBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        contTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "ID do Usuário", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        contTable.setAlignmentX(1.0F);
        contTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        contTable.setGridColor(new java.awt.Color(102, 153, 255));
        contTable.setRowHeight(25);
        contTable.setSelectionBackground(new java.awt.Color(0, 102, 204));
        scrollContTable.setViewportView(contTable);
        if (contTable.getColumnModel().getColumnCount() > 0) {
            contTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            contTable.getColumnModel().getColumn(1).setPreferredWidth(400);
            contTable.getColumnModel().getColumn(1).setMaxWidth(1000);
            contTable.getColumnModel().getColumn(2).setMinWidth(0);
            contTable.getColumnModel().getColumn(2).setPreferredWidth(0);
            contTable.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        reloadCBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        reloadCBtn.setText("Recarregar");
        reloadCBtn.setToolTipText("Retira a seleção da tabela");
        reloadCBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reloadCBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadCBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contPanelLayout = new javax.swing.GroupLayout(contPanel);
        contPanel.setLayout(contPanelLayout);
        contPanelLayout.setHorizontalGroup(
            contPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollContTable, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 31, Short.MAX_VALUE)
                .addGroup(contPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(excContBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editContBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reloadCBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        contPanelLayout.setVerticalGroup(
            contPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contPanelLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(contPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollContTable, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editContBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(excContBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(reloadCBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        addMsgTab.addTab("   Contatos   ", contPanel);

        messagePanel.setBackground(new java.awt.Color(204, 255, 204));

        inboxTable.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        inboxTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Contato", "Mensagem", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inboxTable.setAlignmentX(1.0F);
        inboxTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        inboxTable.setGridColor(new java.awt.Color(102, 153, 255));
        inboxTable.setRowHeight(25);
        inboxTable.setSelectionBackground(new java.awt.Color(0, 102, 204));
        scrollInboxTable.setViewportView(inboxTable);
        if (inboxTable.getColumnModel().getColumnCount() > 0) {
            inboxTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            inboxTable.getColumnModel().getColumn(1).setPreferredWidth(400);
            inboxTable.getColumnModel().getColumn(1).setMaxWidth(1000);
            inboxTable.getColumnModel().getColumn(2).setMinWidth(0);
            inboxTable.getColumnModel().getColumn(2).setPreferredWidth(0);
            inboxTable.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        readBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        readBtn.setText("Ver completa");
        readBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readBtnActionPerformed(evt);
            }
        });

        responseBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        responseBtn.setText("Responder");
        responseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                responseBtnActionPerformed(evt);
            }
        });

        deleteInboxBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        deleteInboxBtn.setText("Excluir");
        deleteInboxBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteInboxBtnActionPerformed(evt);
            }
        });

        sendedBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        sendedBtn.setText("Enviadas");
        sendedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendedBtnActionPerformed(evt);
            }
        });

        messageArea.setColumns(20);
        messageArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        messageArea.setLineWrap(true);
        messageArea.setRows(4);
        messageArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        messageArea.setMaximumSize(new java.awt.Dimension(800, 800));
        scrollRead.setViewportView(messageArea);

        inboxLb.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        inboxLb.setLabelFor(inboxTable);
        inboxLb.setText("Caixa de Entrada");

        reloadIBtn.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        reloadIBtn.setText("Recarregar");
        reloadIBtn.setToolTipText("Retira a seleção da tabela");
        reloadIBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reloadIBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadIBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(messagePanelLayout.createSequentialGroup()
                        .addComponent(inboxLb)
                        .addContainerGap())
                    .addGroup(messagePanelLayout.createSequentialGroup()
                        .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollRead)
                            .addComponent(scrollInboxTable, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(messagePanelLayout.createSequentialGroup()
                                .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(readBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                    .addComponent(responseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteInboxBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
                            .addGroup(messagePanelLayout.createSequentialGroup()
                                .addComponent(sendedBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reloadIBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(inboxLb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollInboxTable, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(messagePanelLayout.createSequentialGroup()
                        .addComponent(readBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(responseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteInboxBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(messagePanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(scrollRead, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(messagePanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendedBtn)
                            .addComponent(reloadIBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        addMsgTab.addTab("   Mensagens   ", messagePanel);

        addDirectPanel.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout addDirectPanelLayout = new javax.swing.GroupLayout(addDirectPanel);
        addDirectPanel.setLayout(addDirectPanelLayout);
        addDirectPanelLayout.setHorizontalGroup(
            addDirectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 804, Short.MAX_VALUE)
        );
        addDirectPanelLayout.setVerticalGroup(
            addDirectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        addMsgTab.addTab("   +   ", addDirectPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addMsgTab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addMsgTab)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       
        int end = JOptionPane.showConfirmDialog(rootPane,
                "Fechar o menu principal encerrará as sessões e a aplicação.\nTem certeza que deseja fazer isso?",
                "Encerrar Programa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
        );
        if (end == 0) {
            this.setVisible(false);
            System.exit(0);
        } else {
            this.getDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {               
            loadContTable();   
            loadInboxTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, 
                "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_formWindowOpened

    private void sendedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendedBtnActionPerformed
        SendboxForm sendboxForm = new SendboxForm(currentUser);
        sendboxForm.setVisible(true);
    }//GEN-LAST:event_sendedBtnActionPerformed

    private void deleteInboxBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteInboxBtnActionPerformed
        int end = 1;
        if (inboxTable.getSelectedRow() != -1) {
            end = JOptionPane.showConfirmDialog(rootPane,
            "Tem certeza que deseja excluir a mensagem para todos?",
            "Excluir Mensagem", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        if (end == 0) {
            try {
                deleteMessage(inboxTable);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane,
                    "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteInboxBtnActionPerformed

    private void responseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_responseBtnActionPerformed
        int rowSelected = inboxTable.getSelectedRow(); 
        if (rowSelected != -1) {
            if (!inboxTable.getValueAt(rowSelected, 0).equals("[Desconhecido]")) {
                AddMesForm addMesForm = new AddMesForm();
                addMesForm.setVisible(true);
            }
            
        } 
    }//GEN-LAST:event_responseBtnActionPerformed

    private void readBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readBtnActionPerformed
        int rowSelected = inboxTable.getSelectedRow();
        if (rowSelected != -1) {
            messageArea.setEnabled(true);
            messageArea.setEditable(false);
            messageArea.setText((String) inboxTable.getValueAt(rowSelected, 1));
        }
    }//GEN-LAST:event_readBtnActionPerformed

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        if (contTable.getSelectedRow() != -1) {
            AddMesForm addMesForm = new AddMesForm();
            addMesForm.setNameField(getContactInitForUpdate());
            addMesForm.setVisible(true);
        }
    }//GEN-LAST:event_sendBtnActionPerformed

    private void editContBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editContBtnActionPerformed
        if (contTable.getSelectedRow() != -1) {
            AddContForm addContForm = new AddContForm();
            addContForm.setTitle("Editar contato");
            addContForm.setVisible(true);
        }
    }//GEN-LAST:event_editContBtnActionPerformed

    private void excContBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excContBtnActionPerformed
        int end = 1;
        if (contTable.getSelectedRow() != -1) {
            end = JOptionPane.showConfirmDialog(rootPane, String.format(
            "Tem certeza que deseja excluir o contato '%s'?",
            contTable.getValueAt(contTable.getSelectedRow(), 0)).toUpperCase(),
            "Excluir Contato", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        if (end == 0) {
            try {
                deleteContact();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane,
                    "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }        
        }
    }//GEN-LAST:event_excContBtnActionPerformed

    private void addUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserBtnActionPerformed
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }//GEN-LAST:event_addUserBtnActionPerformed

    private void addContBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContBtnActionPerformed
        AddContForm addContForm = null;
        int rowSelected = userTable.getSelectedRow();
        Contact currentId = new Contact(currentUser);
        try {
            if (rowSelected != -1) { //table

                Contact contactId = new Contact();
                contactId.setContactId(new User((int) userTable.getValueAt(rowSelected, 0)));

                currentId.setContactId(contactId.getContactId());
                
                if (Validators.validateNotYou(currentId) && Validators.validateFirstCont(currentId, contactId)) {
                    addContForm = new AddContForm(currentId, contactId);
                }
                
            } else { //no table
                addContForm = new AddContForm(currentId);
            }
            addContForm.setVisible(true);
        } catch (TreatmentException treatmentException) {
            JOptionPane.showMessageDialog(rootPane, treatmentException.getMessage(),
                "Ação inválida", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_addContBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int end = JOptionPane.showConfirmDialog(rootPane, String.format(
            "O usuário '%s' será excluido de todas as listas de contatos.\n Tem certeza que deseja fazer isso?",
            currentUser.getUserName().toUpperCase()),
        "Excluir Usuário", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (end == 0) {
            try {
                deleteCurrentUser();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane,
                    "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        try {
            loadUserTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane,
                "Erro na consulta aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loadBtnActionPerformed

    private void reloadIBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadIBtnActionPerformed
        cleanTable((DefaultTableModel) inboxTable.getModel());
        cleanTable((DefaultTableModel) contTable.getModel());
        try {
            loadContTable();
            loadInboxTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane,
                    "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }       
    }//GEN-LAST:event_reloadIBtnActionPerformed

    private void reloadCBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadCBtnActionPerformed
        cleanTable((DefaultTableModel) contTable.getModel());
        try {
            loadContTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane,
                    "Erro inesperado aconteceu! " + ex.getErrorCode() + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }     
    }//GEN-LAST:event_reloadCBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addContBtn;
    private javax.swing.JPanel addDirectPanel;
    private javax.swing.JTabbedPane addMsgTab;
    private javax.swing.JButton addUserBtn;
    private javax.swing.JPanel contPanel;
    private static javax.swing.JTable contTable;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deleteInboxBtn;
    private javax.swing.JButton editContBtn;
    private javax.swing.JButton excContBtn;
    private javax.swing.JLabel inboxLb;
    private static javax.swing.JTable inboxTable;
    private javax.swing.JButton loadBtn;
    private javax.swing.JTextArea messageArea;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JButton readBtn;
    private javax.swing.JButton reloadCBtn;
    private javax.swing.JButton reloadIBtn;
    private javax.swing.JButton responseBtn;
    private javax.swing.JScrollPane scrollContTable;
    private javax.swing.JScrollPane scrollInboxTable;
    private javax.swing.JScrollPane scrollRead;
    private javax.swing.JScrollPane scrollUserTable;
    private javax.swing.JButton sendBtn;
    private javax.swing.JButton sendedBtn;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
    public static void deselect(JTable table) {
        if (table.getSelectedRow() != -1) {
            table.clearSelection();
        }
    }
    private void cleanTable(DefaultTableModel model) {
        int row = model.getRowCount()-1;     
        while (row >= 0) {
            model.removeRow(row);
            row--;
        }      
    }
    private void getDefaultCloseOperation(int DO_NOTHING_ON_CLOSE) {
        throw new UnsupportedOperationException("Not supported yet."); 
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
