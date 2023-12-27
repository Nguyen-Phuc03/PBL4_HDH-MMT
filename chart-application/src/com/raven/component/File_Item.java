package com.raven.component;

import com.raven.event.EventFileReceiver;
import com.raven.event.EventFileSender;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.service.Service;
import com.raven.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class File_Item extends javax.swing.JLayeredPane {

    public File_Item() {
        initComponents();
    }

    public void setFile(File document, Model_File_Sender fileSender) {
           try {
        fileSender.addEvent(new EventFileSender() {
            @Override
            public void onSending(double percentage) {
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {
            }

            @Override
            public void onFinish() {
                progress.setVisible(false);
            }
        });
        
        // Assuming 'document' is a java.io.File instance representing the document
        Service.getInstance().addFile(document, fileSender.getMessage());
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}

  public void setDocument(Model_Receive_File dataDocument) {
    try {
        Service.getInstance().addFileReceiver(dataDocument.getFileID(), new EventFileReceiver() {
            @Override
            public void onReceiving(double percentage) {
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartReceiving() {
            }

            @Override
            public void onFinish(File file) {
                progress.setVisible(false);               
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
}  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.raven.swing.PictureBox();
        progress = new com.raven.swing.Progress();

        progress.setForeground(new java.awt.Color(255, 255, 255));
        progress.setProgressType(com.raven.swing.Progress.ProgressType.CANCEL);

        pic.setLayer(progress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.PictureBox pic;
    private com.raven.swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
