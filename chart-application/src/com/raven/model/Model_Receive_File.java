/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MTMQ
 */
public class Model_Receive_File {
    
    private String nameFile;
    private long fileSize;

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getFileName() {
        return nameFile;
    }

    public void setFileName(String fileName) {
        this.nameFile = fileName;
    }
     private int fileID;

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    
     public Model_Receive_File(Object json) {
    JSONObject obj = (JSONObject) json;
    try {
        if (obj.has("fileID") && obj.has("fileSize")) {
            fileID = obj.getInt("fileID");
            fileSize = obj.getLong("fileSize");
        } else {
            System.err.println("One or more keys are missing in the JSON object.");
        }
        if (obj.has("nameFIle")) {
                nameFile = obj.getString("nameFIle");
            } else {
                System.err.println("One or more keys are missing in the JSON object.");
            } 
            
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
     
     
     public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        try {
            json.put("fileID", fileID);
            json.put("fileSize", fileSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
     
}
