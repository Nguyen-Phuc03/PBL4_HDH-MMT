/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.model;

/**
 *
 * @author MTMQ
 */
public class Model_Receive_File {

    public Model_Receive_File() {
    }
    private int fileID;
    private String nameFIle;
     private long fileSize; 

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    public String getNameFIle() {
        return nameFIle;
    }

    public void setNameFIle(String nameFIle) {
        this.nameFIle = nameFIle;
    }
    

    public Model_Receive_File(int fileID) {
        this.fileID = fileID;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
        
    }
     public Model_Receive_File(int fileID, String nameFIle, long fileSize) {
        this.fileID = fileID;
        this.nameFIle = nameFIle;
        this.fileSize = fileSize;      
    }
}
