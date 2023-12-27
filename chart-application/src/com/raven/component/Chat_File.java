package com.raven.component;

import com.raven.event.EventFileReceiver;
import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Receiver;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.service.Service;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import org.json.JSONException;
import org.json.JSONObject;

public class Chat_File extends javax.swing.JPanel {

    public Chat_File() {
        initComponents();
        setOpaque(false);
      
    }

    public void setFile(String fileName, String size) {
        lbFileName.setText(fileName);
        lbFileSize.setText(size);
    }
    
    
    
    public void addFile(Model_File_Sender fileSender) {
        File file = fileSender.getFile(); 
        addEvent(this,file);
        addFileComponent(file);
}

   public void addFile(Model_Receive_File dataFile) {
    File_Item pic = new File_Item();    
    pic.setDocument(dataFile); 
    add(pic, "wrap");
}
   
    private void addFileComponent(File file) {
    Chat_File fileComponent = new Chat_File();
    fileComponent.setFile(file.getName(), getFileSize(file));
    addEvent(fileComponent, file);
    add(fileComponent, "wrap");
}
    private String getFileSize(File file) {
    long fileSizeInBytes = file.length();
    long fileSizeInKB = fileSizeInBytes / 1024;
    return fileSizeInKB + " KB";
}

    private void addEvent(Component com, File file) {
    com.setCursor(new Cursor(Cursor.HAND_CURSOR));
    com.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {             
                    downloadFile1(file);                
            }
        }
    });
}
    private void downloadFile1(File file) {
    if (Desktop.isDesktopSupported()) {
        // Nếu hỗ trợ Desktop, sử dụng Desktop để mở file
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Không hỗ trợ Desktop, thực hiện tải file ở đây.");
    }
}
    private Icon getIcon(File file) {
    Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
    if (icon == null) {
        // Xử lý biểu tượng null, ví dụ: sử dụng biểu tượng mặc định
        icon = getDefaultIcon();
    }
    return icon;
}

    private Icon getDefaultIcon() {
    // Trả về biểu tượng mặc định hoặc xử lý theo cần thiết
    return UIManager.getIcon("FileView.fileIcon");
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progress1 = new com.raven.swing.Progress();
        jPanel1 = new javax.swing.JPanel();
        lbFileName = new javax.swing.JLabel();
        lbFileSize = new javax.swing.JLabel();

        progress1.setProgressType(com.raven.swing.Progress.ProgressType.FILE);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        lbFileName.setText("My File Name.file");
        jPanel1.add(lbFileName);

        lbFileSize.setForeground(new java.awt.Color(7, 98, 153));
        lbFileSize.setText("5 MB");
        jPanel1.add(lbFileSize);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(progress1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progress1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFileName;
    private javax.swing.JLabel lbFileSize;
    private com.raven.swing.Progress progress1;
    // End of variables declaration//GEN-END:variables
}
