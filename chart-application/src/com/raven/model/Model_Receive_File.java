package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Receive_File {
     private int fileID;
    private String fileName;
    private long fileSize;

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
   
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
      public String getFileName() {  
        return fileName;
    }

    public long getFileSize() {      
        return fileSize;
    }
    
    public Model_Receive_File(int fileID, String fileName, long fileSize) {
         this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
    
    public Model_Receive_File(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            if (obj.has("fileID") && obj.has("fileName") && obj.has("fileSize")) {   
                fileID = obj.getInt("fileID");
                fileSize = obj.getLong("fileSize");
                fileName = obj.getString("fileName");              
            } else {
               // System.out.println("lỗi ở receive_File");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }    
}

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();     
        try {
            json.put("fileID", fileID);
            if (fileName != null) {
                json.put("fileName", fileName);
            }

            json.put("fileSize", fileSize);           
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


}
