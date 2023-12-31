package com.raven.model;

import com.raven.app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class Model_Package_Sender {

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Model_Package_Sender(int fileID, byte[] data, boolean finish, MessageType type,String nameFile) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
        this.type=type;
        this.nameFile=nameFile;
    }
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Model_Package_Sender() {
    }
    private  MessageType type;
    private String nameFile;

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    
    private int fileID;
    private byte[] data;
    private boolean finish;

public JSONObject toJsonObject() {
    try {
        JSONObject json = new JSONObject();
        if (nameFile != null) {
        json.put("nameFile", nameFile);
        }
        json.put("fileID", fileID);
        json.put("data", data);
        json.put("finish", finish);
        json.put("type", type);
       
        
        return json;
    } catch (JSONException e) {
        // Print the stack trace or log a more informative error message
        e.printStackTrace();
        System.err.println("Error creating JSONObject: " + e.getMessage());
        return null;
    }
}

}

