package com.raven.service;

import com.raven.connection.DatabaseConnection;
import com.raven.model.Model_Client;
import com.raven.model.Model_Login;
import com.raven.model.Model_Message;
import com.raven.model.Model_Register;
import com.raven.model.Model_User_Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {

    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public Model_Message register(Model_Register data) {
    Model_Message message = new Model_Message();
    try {
        PreparedStatement pCheck = con.prepareStatement(CHECK_USER);
        pCheck.setString(1, data.getUserName());
        ResultSet rCheck = pCheck.executeQuery();

        if (rCheck.next()) {
            message.setAction(false);
            message.setMessage("User Already Exists");
        } else {
            message.setAction(true);
            try (PreparedStatement pInsertUser = con.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pInsertUser.setString(1, data.getUserName());
                pInsertUser.setString(2, data.getPassword());
                pInsertUser.executeUpdate();
                
                try (ResultSet rInsertUser = pInsertUser.getGeneratedKeys()) {
                    if (rInsertUser.next()) {
                        int userID = rInsertUser.getInt(1);
                        try (PreparedStatement pInsertUserAccount = con.prepareStatement(INSERT_USER_ACCOUNT)) {
                            pInsertUserAccount.setInt(1, userID);
                            pInsertUserAccount.setString(2, data.getUserName());
                            pInsertUserAccount.executeUpdate();
                        }
                        con.commit();
                        message.setAction(true);
                        message.setMessage("Ok");
                        message.setData(new Model_User_Account(userID, data.getUserName(), "", "", true));
                    } else {
                        message.setAction(false);
                        message.setMessage("User insertion failed");
                    }
                }
            }
        }
    } catch (SQLException e) {
        message.setAction(false);
        message.setMessage("Server Error");
        try {
            if (!con.getAutoCommit()) {
                con.rollback();
                con.setAutoCommit(true);
            }
        } catch (SQLException e1) {
            // Log or handle the rollback failure
        }
    }
    return message;
}
    public Model_User_Account login(Model_Login login) throws SQLException {
    Model_User_Account data = null;
    PreparedStatement p = null;
    ResultSet r = null;

    try {
        p = con.prepareStatement(LOGIN);
        p.setString(1, login.getUserName());
        p.setString(2, login.getPassword());
        r = p.executeQuery();

        if (r.next()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            data = new Model_User_Account(userID, userName, gender, image, true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (r != null) {
            try {
                r.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (p != null) {
            try {
                p.close();
            } catch (SQLException e) {
                // Log or handle the exception
                e.printStackTrace();
            }
        }
    }

    return data;
}

    public List<Model_User_Account> getUser(int exitUser) throws SQLException {
        List<Model_User_Account> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement(SELECT_USER_ACCOUNT);
        p.setInt(1, exitUser);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            int userID = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            list.add(new Model_User_Account(userID, userName, gender, image, checkUserStatus(userID)));
        }
        r.close();
        p.close();
        return list;
    }


    private boolean checkUserStatus(int userID) {
        List<Model_Client> clients = Service.getInstance(null).getListClient();
        for (Model_Client c : clients) {
            if (c.getUser().getUserID() == userID) {
                return true;
            }
        }
        return false;
    }

       //  SQL
    private final String LOGIN = "SELECT u.UserID, ua.UserName, ua.Gender, ua.ImageString " + "FROM [user] u " +"JOIN user_account ua ON u.UserID = ua.UserID " +"WHERE u.UserName = ? AND u.Password = ? AND ua.[Status] = '1'";
    private final String SELECT_USER_ACCOUNT = "SELECT UserID, UserName, Gender, ImageString " +
    "FROM user_account " +
    "WHERE [Status] = '1' AND UserID <> ?";

    private final String INSERT_USER = "INSERT INTO [user] (UserName, [Password]) VALUES (?, ?)";

    private final String INSERT_USER_ACCOUNT = "INSERT INTO user_account (UserID, UserName) VALUES (?, ?)";

    private final String CHECK_USER = "SELECT UserID FROM [user] WHERE UserName = ?";
    private final Connection con;
}
