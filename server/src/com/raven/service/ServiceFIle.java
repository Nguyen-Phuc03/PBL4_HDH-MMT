package com.raven.service;

import com.raven.app.MessageType;
import com.raven.connection.DatabaseConnection;
import com.raven.model.Model_File;
import com.raven.model.Model_File_Receiver;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Package_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.model.Model_Send_Message;
import com.raven.swing.blurHash.BlurHash;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ServiceFIle {

    public ServiceFIle() {
        this.con = DatabaseConnection.getInstance().getConnection();
        this.fileReceivers = new HashMap<>();
        this.fileSenders = new HashMap<>();
    }

    public Model_File addFileReceiver(String fileExtension) throws SQLException {
    Model_File data;
    try (PreparedStatement p = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
        p.setString(1, fileExtension);
        p.execute();
        try (ResultSet r = p.getGeneratedKeys()) {
            if (r.next()) { 
                int fileID = r.getInt(1);
                data = new Model_File(fileID, fileExtension);
            } else {
                throw new SQLException("Failed to retrieve generated keys.");
            }
        }
    } 
    return data;
}   


    public void updateBlurHashDone(int fileID, String blurhash) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_BLUR_HASH_DONE);
        p.setString(1, blurhash);
        p.setInt(2, fileID);
        p.execute();
        p.close();
    }

    public void updateDone(int fileID) throws SQLException {
        PreparedStatement p = con.prepareStatement(UPDATE_DONE);
        p.setInt(1, fileID);
        p.execute();
        p.close();
    }
    public void initFile(Model_File file, Model_Send_Message message) throws IOException {
        fileReceivers.put(file.getFileID(), new Model_File_Receiver(message,toFileObject(file)));
    }

    public Model_File getFile(int fileID) throws SQLException {
    Model_File data;
    try (PreparedStatement p = con.prepareStatement(GET_FILE_EXTENSION)) {
        p.setInt(1, fileID);
        try (ResultSet r = p.executeQuery()) {
            if (r.next()) { 
                String fileExtension = r.getString(1);
                data = new Model_File(fileID, fileExtension);
            } else {
                throw new SQLException("No file found with ID: " + fileID);
            }
        }
    } 
    return data;
}   
    public synchronized Model_File initFile(int fileID) throws IOException, SQLException {
        Model_File file;
        if (!fileSenders.containsKey(fileID)) {
            file = getFile(fileID);             
            fileSenders.put(fileID, new Model_File_Sender(file, new File(PATH_FILE + fileID + file.getFileExtension())));
        } else {
            file = fileSenders.get(fileID).getData();
        }
        return file;
    }
  
    public byte[] getFileData(long currentLength, int fileID) throws IOException, SQLException {
        initFile(fileID);
        return fileSenders.get(fileID).read(currentLength);
    }

    public long getFileSize(int fileID) {
        return fileSenders.get(fileID).getFileSize();
    }

    public void receiveFile(Model_Package_Sender dataPackage) throws IOException {
        if (!dataPackage.isFinish()) {
            fileReceivers.get(dataPackage.getFileID()).writeFile(dataPackage.getData());
        } else {
            fileReceivers.get(dataPackage.getFileID()).close();
        }
    }

    public Model_Send_Message closeFile(Model_Receive_Image dataImage,Model_Receive_File dataFile) throws IOException, SQLException {
        Model_File_Receiver file1 = fileReceivers.get(dataImage.getFileID()); 
         Model_File_Receiver file = fileReceivers.get(dataFile.getFileID());     
        if (file1.getMessage().getMessageType() == MessageType.IMAGE.getValue()||file.getMessage().getMessageType() == MessageType.FILE.getValue()) {
            
            if(file1.getMessage().getMessageType() == MessageType.IMAGE.getValue()){
            file1.getMessage().setText("");
            String blurhash = convertFileToBlurHash(file1.getFile(), dataImage);
            updateBlurHashDone(dataImage.getFileID(), blurhash);  
            fileReceivers.remove(dataImage.getFileID());       
            }
            
            else if(file.getMessage().getMessageType() == MessageType.FILE.getValue()){               
            file.getMessage().setText("");                  
             String blurhash = convertFileToBlurHash(file.getFile(), dataFile);
                updateBlurHashDone(dataFile.getFileID(), blurhash);
  
              fileReceivers.remove(dataFile.getFileID());           
                }
        } else {
            updateDone(dataImage.getFileID()); 
            updateDone(dataFile.getFileID());
              
        }
        //  Get message to send to target client when file receive finish
       return (file1.getMessage().getMessageType() == MessageType.IMAGE.getValue()) ? file1.getMessage() : file.getMessage();
      
    }
    private String convertFileToBlurHash(File file, Model_Receive_Image dataImage) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Dimension size = getAutoSize(new Dimension(img.getWidth(), img.getHeight()), new Dimension(200, 200));
        //  Convert image to small size
        BufferedImage newImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        g2.drawImage(img, 0, 0, size.width, size.height, null);
        String blurhash = BlurHash.encode(newImage);
        dataImage.setWidth(size.width);
        dataImage.setHeight(size.height);
        dataImage.setImage(blurhash);
   
        return blurhash;
    }  
    private byte[] convertFileToBlurHash1(File file, Model_Receive_File dataFile) throws IOException {
        byte[] fileData = readFileData(file);
        long fileSize = file.length();
        String fileName = file.getName();
        
        dataFile.setFileName(fileName);
        dataFile.setFileSize(fileSize);
        return fileData;
    }
    
    
    
    
    
    private String convertFileToBlurHash(File file, Model_Receive_File dataFile) throws IOException {
    String fileName = file.getName();
    long fileSize = file.length();
    dataFile.setFileName(fileName);
    dataFile.setFileSize(fileSize);
    return dataFile.getFileName();
}
    
    

    
    
    private byte[] readFileData(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // Đọc dữ liệu từ FileInputStream vào một mảng byte
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            return buffer;
        }
    }
    private Dimension getAutoSize(Dimension fromSize, Dimension toSize) {
        int w = toSize.width;
        int h = toSize.height;
        int iw = fromSize.width;
        int ih = fromSize.height;
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    private File toFileObject(Model_File file) {
        return new File(PATH_FILE + file.getFileID()+ file.getFileExtension());
    }

    private final String PATH_FILE = "server_data/";
    private final String INSERT = "INSERT INTO files (FileExtension) VALUES (?)";
    private final String UPDATE_BLUR_HASH_DONE = "UPDATE files SET BlurHash=?, Status='1' WHERE FileID=?";
    private final String UPDATE_DONE = "UPDATE files SET Status='1' WHERE FileID=? ";
    private final String GET_FILE_EXTENSION = "SELECT FileExtension FROM files WHERE FileID=?";
    
    
    
    private final String INSERT1 = "INSERT INTO files (BlurHash) VALUES (?)";

    private final String UPDATE_DONE1 = "UPDATE file SET Status='1' WHERE FileID=? ";
    private final String GET_FILE_EXTENSION1 = "SELECT FileExtension FROM file WHERE FileID=?";
    //  Instance
    private final Connection con;
    private final Map<Integer, Model_File_Receiver> fileReceivers;
    private final Map<Integer, Model_File_Sender> fileSenders;
}
