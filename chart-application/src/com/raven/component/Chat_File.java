package com.raven.component;

import com.raven.event.EventFileReceiver;
import com.raven.event.EventFileSender;
import com.raven.main.Main;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

public class Chat_File extends javax.swing.JPanel {

   
      public Chat_File() {
        initComponents();
         setOpaque(false);
     
        }     
       public void setFile(String size) {
       // lbFileName.setText(fileName);
        lbFileSize.setText(size);
    }
        public void addFile(Model_File_Sender fileSender) {
        lbFileName.setText(fileSender.getFile().getName());     
        File file = fileSender.getFile();      
        addEvent(this,file);
        addFileComponent(file);        
        revalidate();
        repaint();      
    }
       private void addFileComponent(File file) {
       Chat_File fileComponent = new Chat_File();
        fileComponent.setFile(getFileSize(file));
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
                    openfile(file);                
            }
        }
    });
}
    private void openfile(File file) {
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
          
        public void addFile(Model_Receive_File file){ 
        Chat_File trol =new Chat_File();            
        lbFileName.setText(file.getFileName());
        int idfile = file.getFileID();       
        trol.setDocument(file);   
        jPanel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            openFile(idfile,file.getFileName());
        }
    });  
        add(trol, "wrap");
    
        revalidate();
        repaint();
   }    
      private void openFile(int idfile,String fileName) {
    String parentDirectoryPath = "client_data/";

    String fileNameDocx = idfile + ".docx";
    String fileNamePdf = idfile + ".pdf";
    String fileNametxt = idfile + ".txt";
    String filePathDocx = parentDirectoryPath + File.separator + fileNameDocx;
    String filePathPdf = parentDirectoryPath + File.separator + fileNamePdf;
    String filePathtxt = parentDirectoryPath + File.separator + fileNametxt;
    File fileToOpen;
    if (new File(filePathDocx).exists()) {
        fileToOpen = new File(filePathDocx);
    } else if (new File(filePathPdf).exists()) {
        fileToOpen = new File(filePathPdf);
    } else if (new File(filePathPdf).exists()) {
        fileToOpen = new File(filePathtxt);
    } else {
        System.out.println("Không tìm thấy tệp: " + fileNameDocx + " hoặc " + fileNamePdf+ " hoặc " + fileNametxt);
        return;
    }
    JFileChooser ch = new JFileChooser();
    ch.setSelectedFile(new File(fileName)); 
    ch.setMultiSelectionEnabled(true);
                 ch.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                    @Override
                    public String getDescription() {
                        return "File";
                    }
                });
    int result = ch.showSaveDialog(Main.getFrames()[0]);

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = ch.getSelectedFile();
        String destinationFilePath = selectedFile.getAbsolutePath();
        try {
            Files.copy(fileToOpen.toPath(), new File(destinationFilePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            String successMessage = "Đã tải tệp: " +" vào: " + destinationFilePath;
            JOptionPane.showMessageDialog(null, successMessage, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi tải tệp: " + e.getMessage());
        }
    }
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
