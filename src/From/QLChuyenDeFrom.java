/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package From;

import EduSysDAO.ChuyenDeDAO;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.ChuyenDe;
import utils.*;


/**
 *
 * @author User
 */
public class QLChuyenDeFrom extends javax.swing.JInternalFrame {

    /**
     * Creates new form QLChuyenDeFrom
     */
    ChuyenDeDAO dao = new ChuyenDeDAO();
    int row = 1;
    String linKImg = "";

    public QLChuyenDeFrom() {
        initComponents();
        FillTable();
    }

    void FillTable() {
        DefaultTableModel moldel = (DefaultTableModel) tblDanhsach.getModel();
        moldel.setRowCount(0);
        try {
            List<ChuyenDe> list = dao.selecALL();
            for (ChuyenDe cd : list) {
                moldel.addRow(new Object[]{cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(),
                    cd.getThoiLuong(), cd.getHinh(), cd.getMoTa()});
            }
        } catch (Exception e) {
        }
    }

    void init() {
    }

    void insert() {
        ChuyenDe cd = getFrom();
        try {
            dao.insert(cd);
            this.FillTable();
            this.clearFrom();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm mới thất bại!");
        }

    }

    void update() {
        ChuyenDe nv = getFrom();
        try {
            dao.update(nv);
            this.FillTable();
            MsgBox.alert(this, "Cập nhập thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhập thất bại!");
        }
    }

    void delete() {
        if (MsgBox.comfirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            try {
                dao.delete(txtMaChuyende.getText());
                this.FillTable();
                this.clearFrom();
                MsgBox.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
            }

        }
    }

    void clearFrom() {
        ChuyenDe nv = new ChuyenDe();

        this.setFrom(nv);
        this.row = -1;
        this.updateStatus();
    }

    void edit() {
        tabs.setSelectedIndex(1);
        String manv = (String) tblDanhsach.getValueAt(row, 0);
        ChuyenDe nv = dao.selectById(manv);
        this.setFrom(nv);
        tabs.setSelectedIndex(0);
//        this.updateStatus();
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblDanhsach.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblDanhsach.getRowCount() - 1;
        this.edit();

    }

    void setFrom(ChuyenDe cd) {
        txtMaChuyende.setText(cd.getMaCD());
        txtTenchuyende.setText(cd.getTenCD());
        txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        txtHocPhi.setText(cd.getHocPhi() + "");
        txtMotachuyende.setText(cd.getMoTa());
        if (cd.getHinh() != null) {
            lblhinh.setIcon(XImage.readLogo(cd.getHinh()));
        } else {
            lblhinh.setIcon(XImage.readLogo("noImage.png"));
        }
    }

    ChuyenDe getFrom() {
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(txtMaChuyende.getText());
        cd.setTenCD(txtTenchuyende.getText());
        cd.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        cd.setHocPhi(Double.valueOf(txtHocPhi.getText()));
        cd.setHinh(linKImg);
        cd.setMoTa(txtMotachuyende.getText());
        return cd;
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblDanhsach.getRowCount() - 1);
        // Trạng thái form
        btnInsert.setEnabled(!edit);
        btnUpdate.setEnabled(!edit);
        btnDelete.setEnabled(!edit);
        //Trạng thái điêu hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void selectImage() {
        try {
            JFileChooser jfc = new JFileChooser();
            // mặc định chỉ được chọn ảnh
            FileFilter imgFilter = new FileNameExtensionFilter("Image Only", ImageIO.getReaderFileSuffixes());
            jfc.setFileFilter(imgFilter);
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                XImage.saveLogo(file); // lưu hình vào thư mục logos
                ImageIcon icon = XImage.readLogo(file.getName());
                this.lblhinh.setIcon(icon);
                linKImg = file.getName();
                this.lblhinh.setToolTipText(file.getName()); // giữ tên hình trong tooltip
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Chọn ảnh thất bại");
        }
//        JFileChooser fileChooser = new JFileChooser();
//        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //nếu người dùng đã chọn đc file
//            File file = fileChooser.getSelectedFile();    //lấy file người dùng chọn
//            if (XImage.saveLogo(file)) {  //sao chép file đã chọn thư mục logos
//                // Hiển thị hình lên form
//                lblhinh.setIcon(XImage.readLogo(file.getName())); //file.getName(); lấy tên của file
//                //ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
//                //void setIcon(ImageIcon icon) set Icon cho lbl
//                lblhinh.setToolTipText(file.getName());
//            }
//        }
    }

//    public ImageIcon image() {
//        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "JPG & GIF Images", "jpg", "gif", "png");
//        chooser.setFileFilter(filter);
//        int returnVal = chooser.showOpenDialog(null);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            linKImg = String.valueOf(chooser.getSelectedFile());
//            System.out.println(linKImg);
//            ImageIcon imageIcon = new ImageIcon(linKImg);
//            Image image = imageIcon.getImage();
//            Image image1 = image.getScaledInstance(80, 80, 1000);
//            imageIcon = new ImageIcon(image1);
//            return imageIcon;
//        }
//        return null;
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblhinh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaChuyende = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenchuyende = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMotachuyende = new javax.swing.JTextArea();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhsach = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel2.setText("Hình logo");

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        lblhinh.setBackground(new java.awt.Color(0, 0, 204));
        lblhinh.setForeground(new java.awt.Color(0, 0, 255));
        lblhinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblhinh, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblhinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setText("Mã chuyên đề");

        jLabel4.setText("Tên chuyên đề");

        jLabel5.setText("Thời lượng (giờ)");

        jLabel6.setText("Học phí");

        jLabel7.setText("Mô tả chuyên đề");

        txtMotachuyende.setColumns(20);
        txtMotachuyende.setRows(5);
        jScrollPane2.setViewportView(txtMotachuyende);

        jScrollPane3.setViewportView(jScrollPane2);

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jButton4.setText("Mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblHinh))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(txtThoiLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenchuyende, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(txtMaChuyende, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtHocPhi)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMaChuyende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenchuyende, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(jButton4)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(12, 12, 12))
        );

        tabs.addTab("CẬP NHẬP", jPanel1);

        tblDanhsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH"
            }
        ));
        tblDanhsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhsachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhsach);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        if (tblDanhsach.getSelectedRow()<0) {
            MsgBox.alert(this, "Mời bạn chọn chuyên đề muốn xóa");
        }
        else if (this.checkT() == true && this.check() == true) {
            this.insert();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (this.check()) {
            this.update();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDanhsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhsachMouseClicked
        this.row = tblDanhsach.getSelectedRow();
        this.edit();
    }//GEN-LAST:event_tblDanhsachMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked

    }//GEN-LAST:event_jPanel3MouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tblDanhsach.getSelectedRow()<0) {
            MsgBox.alert(this, "Mời bạn chọn chuyên đề muốn xóa");
        }else{
        this.delete();
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void lblhinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhinhMouseClicked
//        ChonAnh();
        selectImage();
//       lblhinh.setIcon(image());
    }//GEN-LAST:event_lblhinhMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      clearFrom();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblhinh;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDanhsach;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyende;
    private javax.swing.JTextArea txtMotachuyende;
    private javax.swing.JTextField txtTenchuyende;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
private boolean checkT() {
        if (this.dao.selectById(this.txtMaChuyende.getText()) != null) {
            MsgBox.alert(this, "Mã chuyên đề tồn tại");
            this.txtMaChuyende.requestFocus();
            return false;
        }
        return true;
    }

    private boolean check() {
        //Check mã chuyên đề
        if (this.txtMaChuyende.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập mã chuyên đề!");
            this.txtMaChuyende.requestFocus();
            return false;
        } else if (this.txtMaChuyende.getText().length() != 5) {
            MsgBox.alert(this, "Mã chuyên đề phải 5 ký tự!");
            this.txtMaChuyende.requestFocus();
            return false;
        }

        //Check tên chuyên đề
        if (this.txtTenchuyende.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập tên chuyên đề!");
            this.txtTenchuyende.requestFocus();
            return false;
        } else if (this.txtTenchuyende.getText().length() < 3) {
            MsgBox.alert(this, "Tên chuyên đề phải trên 3 ký tự!");
            this.txtTenchuyende.requestFocus();
            return false;
        }

        //Check thời lượng
        if (this.txtThoiLuong.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập thời lượng!");
            this.txtThoiLuong.requestFocus();
            return false;
        }
        int thoiluong;
        try {
            thoiluong = Integer.parseInt(this.txtThoiLuong.getText());
            if (thoiluong < 0) {
                MsgBox.alert(this, "Thời lượng phải lớn hơn 0!");
                this.txtThoiLuong.requestFocus();
                return false;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Thời lượng phải là số!");
            this.txtThoiLuong.requestFocus();
            return false;
        }

        //Check học phí
        if (this.txtHocPhi.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập học phí!");
            this.txtHocPhi.requestFocus();
            return false;
        }
        float hocPhi;
        try {
            hocPhi = Float.parseFloat(this.txtHocPhi.getText());
            if (hocPhi < 0) {
                MsgBox.alert(this, "Học phí phải lớn hơn 0!");
                this.txtHocPhi.requestFocus();
                return false;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Học phí phải là số!");
            this.txtHocPhi.requestFocus();
            return false;
        }

        return true;
    }
}
