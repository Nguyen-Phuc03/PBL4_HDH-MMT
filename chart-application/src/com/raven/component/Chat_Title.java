package com.raven.component;

import com.raven.model.Label_Name;
import com.raven.model.Model_Login;
import com.raven.model.Model_User_Account;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chat_Title extends javax.swing.JPanel {

    public Model_User_Account getUser() {
        return user;
    }
    private Model_User_Account user;

    public Chat_Title() {
        initComponents();
    }

    public void setUserName(Model_User_Account user) {
        this.user = user;
        lbName.setText(user.getUserName());
        lbName1.setText(Label_Name.name);
        if (user.isStatus()) {
            statusActive();
        } else {
            setStatusText("Offline");
        }
    }

    public void updateUser(Model_User_Account user) {
        if (this.user == user) {
            lbName.setText(user.getUserName());
            if (user.isStatus()) {
                statusActive();
            } else {
                setStatusText("Offline");
            }
        }
    }

    private void statusActive() {
        lbStatus.setText("Online");
        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
    }

    private void setStatusText(String text) {
        lbStatus.setText(text);
        lbStatus.setForeground(new Color(160, 160, 160));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        lbName = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        layer1 = new javax.swing.JLayeredPane();
        lbName1 = new javax.swing.JLabel();
        lbStatus1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(229, 229, 229));

        layer.setLayout(new java.awt.GridLayout(0, 1));

        lbName.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lbName.setForeground(new java.awt.Color(66, 66, 66));
        lbName.setText("Name");
        layer.add(lbName);

        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
        lbStatus.setText("Online");
        layer.add(lbStatus);

        layer1.setLayout(new java.awt.GridLayout(0, 1));

        lbName1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lbName1.setForeground(new java.awt.Color(66, 66, 66));
        lbName1.setText("Name");
        layer1.add(lbName1);

        lbStatus1.setForeground(new java.awt.Color(40, 147, 59));
        lbStatus1.setText("Online");
        layer1.add(lbStatus1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(layer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 361, Short.MAX_VALUE)
                .addComponent(layer1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(layer1)
                    .addComponent(layer))
                .addGap(3, 3, 3))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layer;
    private javax.swing.JLayeredPane layer1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbName1;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbStatus1;
    // End of variables declaration//GEN-END:variables
}
