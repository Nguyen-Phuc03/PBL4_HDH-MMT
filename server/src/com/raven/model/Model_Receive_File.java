package com.raven.model;

public class Model_Receive_File {

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

    public Model_Receive_File() {
    }

    private int fileID;
    private String fileName;
    private long fileSize;  
}
