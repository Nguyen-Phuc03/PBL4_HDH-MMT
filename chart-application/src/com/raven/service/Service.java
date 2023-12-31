package com.raven.service;

import com.raven.event.EventFileReceiver;
import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Receiver;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_Message;
import com.raven.model.Model_Send_Message;
import com.raven.model.Model_User_Account;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Service {
    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 12345;
    private final String IP = "localhost";
 
    private Model_User_Account user;
    private List<Model_File_Sender> fileSender;
    private List<Model_File_Receiver> fileReceiver;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    //  list user
                    List<Model_User_Account> users = new ArrayList<>();
                    for (Object o : os) {
                        Model_User_Account u = new Model_User_Account(o);
                        if (u.getUserID() != user.getUserID()) {
                            users.add(u);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if (status) {
                        //  connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else {
                        //  disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });
            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    Model_Receive_Message message = new Model_Receive_Message(os[0]);
                  //  System.out.println(message.get);
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });
            client.on("userOnline", new Emitter.Listener() {
             @Override
                 public void call(final Object... args) {
                    String successMessage = (String) args[0];
                    JOptionPane.showMessageDialog(null, successMessage, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            
            });
             client.on("checkuser", new Emitter.Listener() {
             @Override
                 public void call(final Object... args) {
                    String successMessage = (String) args[0];
                    JOptionPane.showMessageDialog(null, successMessage, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            
            });
            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }

    public Model_File_Sender addFile(File file, Model_Send_Message message) throws IOException {
        Model_File_Sender data = new Model_File_Sender(file, client, message);
        message.setFile(data);
        fileSender.add(data);
        String filename=data.getFile().getName();
        //  For send file one by one
        if (fileSender.size() == 1) {
            data.initSend(filename);
        }
        return data;
    }
//    public Model_File_Sender addFileForButtonFile(File file, Socket socket, Model_Send_Message message) throws IOException {
//    Model_File_Sender data = new Model_File_Sender(file, socket, message);
//    // Không gọi message.setFile(data) trong trường hợp này
//    fileSender.add(data);
//     String filename=data.getFile().getName();
//    // For send file one by one
//    if (fileSender.size() == 1) {
//        data.initSend(filename);
//    }
//    return data;
//}


    public void fileSendFinish(Model_File_Sender data) throws IOException {
        fileSender.remove(data);
         String filename=data.getFile().getName();
        if (!fileSender.isEmpty()) {
            //  Start send new file when old file sending finish
            fileSender.get(0).initSend(filename);
        }
    }

    public void fileReceiveFinish(Model_File_Receiver data) throws IOException {
        fileReceiver.remove(data);
        if (!fileReceiver.isEmpty()) {
            fileReceiver.get(0).initReceive();
        }
    }

    public void addFileReceiver(int fileID, EventFileReceiver event) throws IOException {
        Model_File_Receiver data = new Model_File_Receiver(fileID, client, event);
        fileReceiver.add(data);
        if (fileReceiver.size() == 1) {
            data.initReceive();
        }
    }

    public Socket getClient() {
        return client;
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

    private void error(Exception e) {
        System.err.println(e);
    }
}
