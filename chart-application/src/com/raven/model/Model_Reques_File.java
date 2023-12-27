package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Reques_File {

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    public Model_Reques_File(int fileID, long currentLength) {
        this.fileID = fileID;
//        this.fileName = fileName;
        this.currentLength = currentLength;
    }
    
    public Model_Reques_File() {
    }

    private int fileID;
    private String fileName;

    
    private long currentLength;
    
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
//            json.put("fileName", fileName);
            json.put("currentLength", currentLength);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
