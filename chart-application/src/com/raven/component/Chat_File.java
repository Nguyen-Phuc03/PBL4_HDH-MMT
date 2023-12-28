package com.raven.component;

import com.raven.event.EventFileReceiver;
import com.raven.event.EventFileSender;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.service.Service;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class Chat_File extends javax.swing.JPanel {
     private EventFileReceiver event;
    public Chat_File() {
        initComponents();
        setOpaque(false);
      
    }
    public void setFile(File document, Model_File_Sender fileSender) {
           try {
        fileSender.addEvent(new EventFileSender() {
            @Override
            public void onSending(double percentage) {
                progress1.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {
            }

            @Override
            public void onFinish() {
                progress1.setVisible(false);
            }
        });
        
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
                progress1.setValue((int) percentage);
            }
            @Override
            public void onStartReceiving() {
            }
            @Override
            public void onFinish(File file) {
                progress1.setVisible(false); 
                
            }
        });
    } catch (IOException e) {
        e.printStackTrace();
    }
}  
public void addFile(Model_Receive_File dataFile) {
    int idfile = dataFile.getFileID();
    Chat_File pic = new Chat_File();  
    pic.setDocument(dataFile);
    addEvent(this,idfile); 
}
    private void addEvent(Component com,int idfile) {
    com.setCursor(new Cursor(Cursor.HAND_CURSOR));
    com.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {             
                        openFile(idfile);     
            }
        }
         });
        }
    private void openFile(int idfile) {
            String parentDirectoryPath = "client_data/";
            String fileNameDocx = idfile + ".docx";
            String fileNamePdf = idfile + ".pdf";

             String filePathDocx = parentDirectoryPath + File.separator + fileNameDocx;
                String filePathPdf = parentDirectoryPath + File.separator + fileNamePdf;

                File fileToOpen;

             if (new File(filePathDocx).exists()) {
                 fileToOpen = new File(filePathDocx);
             } else if (new File(filePathPdf).exists()) {
                 fileToOpen = new File(filePathPdf);
             } else {
                 System.out.println("Không tìm thấy tệp: " + fileNameDocx + " hoặc " + fileNamePdf);
                 return;
                }
                try {
                    Desktop.getDesktop().open(fileToOpen);
                    System.out.println("File opened: " + fileToOpen.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error opening file: " + e.getMessage());
                }         
        }
   
   
    
   
  
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
     public void setFile(String fileName, String size) {
        lbFileName.setText(fileName);
        lbFileSize.setText(size);
    }
    
    
    
    public void addFile(Model_File_Sender fileSender) {
        File file = fileSender.getFile(); 
        addEvent(this,file);
        addFileComponent(file);
        jButton1.setVisible(false);
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
        jButton1 = new javax.swing.JButton();
        lbFileSize = new javax.swing.JLabel();

        progress1.setProgressType(com.raven.swing.Progress.ProgressType.FILE);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        lbFileName.setText("My File Name.file");
        jPanel1.add(lbFileName);

        jButton1.setBackground(new java.awt.Color(242, 242, 242));
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFileName;
    private javax.swing.JLabel lbFileSize;
    private com.raven.swing.Progress progress1;
    // End of variables declaration//GEN-END:variables
}
