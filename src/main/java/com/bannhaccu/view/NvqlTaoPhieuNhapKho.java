/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.view;

import com.bannhaccu.dao.DAO;
import com.bannhaccu.dao.PhieuNhapKhoDAO;
import com.bannhaccu.dao.SanPhamDAO;
import com.bannhaccu.dao.SanPhamDuocNhapKhoDAO;
import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.PhieuNhapKho;
import com.bannhaccu.model.SanPham;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TienAnh
 */
public class NvqlTaoPhieuNhapKho extends javax.swing.JFrame {

    private NhanVien nv;
    private SanPhamDAO spDAO;
    private PhieuNhapKhoDAO pnkDAO;
    private SanPhamDuocNhapKhoDAO spdnkDAO;

    /**
     * Creates new form NvqlTaoPhieuNhapKho
     */
    public NvqlTaoPhieuNhapKho(NhanVien nv) {
        initComponents();
        this.nv = nv;
        spDAO = new SanPhamDAO();
        pnkDAO = new PhieuNhapKhoDAO();
        spdnkDAO = new SanPhamDuocNhapKhoDAO();
        lbUsername.setText(nv.getTendangnhap());
        tfNhanVienTaoPhieu.setText(nv.getTendangnhap());
        initTableProduct();
    }

    private void initTableProduct() {
        // Set default focus when init
        tableSanpham.setRequestFocusEnabled(true);
        tableSanpham.setRowSelectionInterval(0, 0);
        tableSanpham.setColumnSelectionInterval(1, 1);
        tableSanpham.editCellAt(0, 1);
        tableSanpham.requestFocus();

        // Handle Enter. If focus mã sản phẩm ---enter--> focus số lượng
        Action handleEnter = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tableSanpham.getCellEditor().stopCellEditing(); // store user input
                if (tableSanpham.getSelectedColumn() == 1 && tableSanpham.getModel().getValueAt(tableSanpham.getSelectedRow(), 1) != null) {
                    // Focus "Số lượng nhập"
                    tableSanpham.editCellAt(tableSanpham.getSelectedRow(), 8);
                    tableSanpham.requestFocus();
                }
            }
        };
        tableSanpham.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "handleEnter");
        tableSanpham.getActionMap().put("handleEnter", handleEnter);

        // Handle fill các thông tin trống
        DefaultTableModel table = (DefaultTableModel) tableSanpham.getModel();
        table.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                DefaultTableModel table = (DefaultTableModel) tableSanpham.getModel();
                if (table.getValueAt(e.getLastRow(), 1) != null && table.getValueAt(e.getLastRow(), 0) == null) {
                    // if valid id => fill info
                    if (e.getColumn() == 1) {
                        try {
                            Object maSanPham = table.getValueAt(e.getLastRow(), 1);
                            SanPham sp = spDAO.getSanphamByID(new Integer(maSanPham + ""));
                            if (sp != null) {
                                table.setValueAt(sp.getTen(), e.getLastRow(), 2);
                                table.setValueAt(sp.getMausac(), e.getLastRow(), 3);
                                table.setValueAt(sp.getHang().getTen(), e.getLastRow(), 4);
                                table.setValueAt(sp.getTheloai().getTen(), e.getLastRow(), 5);
                                table.setValueAt(sp.getGia(), e.getLastRow(), 6);
                                table.setValueAt(sp.getSoluong(), e.getLastRow(), 7);
                            } else {
                                JOptionPane.showMessageDialog(null, "Mã sản phẩm không tồn tại");
                                table.setValueAt(null, e.getLastRow(), 1);
                            }
                        } catch (java.lang.NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Mã sản phẩm phải là chữ số");
                            table.setValueAt(null, e.getLastRow(), 1);
                        }
                    } else if (e.getColumn() == 8) {
                        if (table.getValueAt(e.getLastRow(), 8) != null) {
                            try {
                                Double total = new Integer(table.getValueAt(e.getLastRow(), 8) + "") * (Double) (table.getValueAt(e.getLastRow(), 6));
                                table.setValueAt(table.getRowCount(), e.getLastRow(), 0);
                                table.setValueAt(total, e.getLastRow(), 9);
                                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                                formatter.applyPattern("#,###,###,###");
                                tfTongtien.setText(formatter.format(calculateTotal()).toString());
                                addRow();
                            } catch (java.lang.NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Số lượng phải là chữ số");
                                table.setValueAt(null, e.getLastRow(), 8);
                            }

                        }
                    }
                }
            }

        });
    }

    private double calculateTotal() {
        double total = 0;
        for (int i = 0; i < tableSanpham.getModel().getRowCount(); i++) {
            if (tableSanpham.getModel().getValueAt(i, 1) != null) {
                total += (Double) tableSanpham.getModel().getValueAt(i, 9);
            }
        }
        return total;
    }

    private void addRow() {
        DefaultTableModel table = (DefaultTableModel) tableSanpham.getModel();
        Object[] emptyData = {null, null, null, null, null, null, null, null, null, null};
        table.addRow(emptyData);
        tableSanpham.editCellAt(tableSanpham.getRowCount(), 1);
        tableSanpham.requestFocus();
        tableSanpham.setRowSelectionInterval(tableSanpham.getRowCount() - 1, tableSanpham.getRowCount() - 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lbUsername = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSanpham = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfTongtien = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfNhanVienTaoPhieu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 48));

        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lbUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbUsername.setText("username");

        jButton2.setText("Trở lại");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 540, Short.MAX_VALUE)
                .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(lbUsername)
                    .addComponent(jButton2))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        tableSanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên sản phẩm", "Màu", "Hãng", "Thể loại", "Giá", "Tồn kho", "Số lượng nhập", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSanpham.setName(""); // NOI18N
        tableSanpham.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableSanpham);
        if (tableSanpham.getColumnModel().getColumnCount() > 0) {
            tableSanpham.getColumnModel().getColumn(0).setPreferredWidth(10);
            tableSanpham.getColumnModel().getColumn(1).setPreferredWidth(30);
            tableSanpham.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableSanpham.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableSanpham.getColumnModel().getColumn(4).setPreferredWidth(50);
            tableSanpham.getColumnModel().getColumn(5).setPreferredWidth(50);
            tableSanpham.getColumnModel().getColumn(7).setPreferredWidth(50);
            tableSanpham.getColumnModel().getColumn(8).setPreferredWidth(60);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Tổng tiền");

        tfTongtien.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tfTongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfTongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton4.setText("Tạo phiếu nhập kho");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nhân viên tạo phiếu");

        tfNhanVienTaoPhieu.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfNhanVienTaoPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNhanVienTaoPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Dangnhap().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new NvqlMain(nv).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Is any product fill
        for (int i = 0; i < tableSanpham.getModel().getRowCount(); i++) {
            if (tableSanpham.getModel().getValueAt(i, 0) != null) {
                break;
            }
            if (i == tableSanpham.getModel().getRowCount() - 1) {
                return;
            }
        }

        try {
            // Bắt đầu transaction
            DAO.getConn().setAutoCommit(false);
            // 1. Lưu phiếu nhập kho
            PhieuNhapKho pnk = pnkDAO.storePhieuNhapKho(calculateTotal(), nv);
            // 2. Lưu sản phẩm được nhập thêm vào kho
            spdnkDAO.storeSanPhamDuocNhapKho(tableSanpham.getModel(), pnk);
            // Commit Transaction
            DAO.getConn().commit();
            
            JOptionPane.showMessageDialog(null, "Tạo phiếu nhập kho thành công");
            new NvqlMain(nv).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            try {
                // RollBack Transaction
                DAO.getConn().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(NvqlTaoPhieuNhapKho.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NvqlTaoPhieuNhapKho.class
                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NvqlTaoPhieuNhapKho.class
                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NvqlTaoPhieuNhapKho.class
                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NvqlTaoPhieuNhapKho.class
                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NhanVien nv = new NhanVien();
                nv.setTendangnhap("ADMIN01");
                nv.setVitri(9);
                nv.setId(4);
                new NvqlTaoPhieuNhapKho(nv).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTable tableSanpham;
    private javax.swing.JTextField tfNhanVienTaoPhieu;
    private javax.swing.JTextField tfTongtien;
    // End of variables declaration//GEN-END:variables
}
