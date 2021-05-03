/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.view.nhanvien;

import com.bannhaccu.dao.NhanVienDAO;
import com.bannhaccu.dao.SanPhamDAO;
import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.SanPham;
import com.bannhaccu.view.Dangnhap;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author TienAnh
 */
public class NvbhBanhang extends javax.swing.JFrame {

    private NhanVien nv;
    private SanPhamDAO spDAO;

    /**
     * Creates new form NvbhBanhang
     */
    public NvbhBanhang(NhanVien nv) {
        initComponents();
        initTableProduct();
        this.nv = nv;
        this.spDAO = new SanPhamDAO();
        lbUsername.setText(this.nv.getTendangnhap());
    }

    public NvbhBanhang(NhanVien nv, TableModel tm) {
        initComponents();
        tableSanpham.setModel(tm);
        initTableProduct();
        this.nv = nv;
        this.spDAO = new SanPhamDAO();
        lbUsername.setText(this.nv.getTendangnhap());
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
                    tableSanpham.editCellAt(tableSanpham.getSelectedRow(), 5);
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
                                // Update soluong
                                for (int i = 0; i < table.getRowCount(); i++) {
                                    if (i != e.getLastRow() && table.getValueAt(i, 1) != null && new Integer(table.getValueAt(i, 1)+"") == sp.getId()) {
                                        sp.setSoluong(sp.getSoluong() - new Integer(table.getValueAt(i, 5)+""));
                                    }
                                }
                                
                                if (sp.getSoluong() <= 0) {
                                    JOptionPane.showMessageDialog(null, "Mã sản phẩm hết hàng");
                                    table.setValueAt(null, e.getLastRow(), 1);
                                } else {
                                    table.setValueAt(sp.getTen(), e.getLastRow(), 2);
                                    table.setValueAt(sp.getGia(), e.getLastRow(), 3);
                                    table.setValueAt(sp.getSoluong(), e.getLastRow(), 4);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Mã sản phẩm không tồn tại");
                                table.setValueAt(null, e.getLastRow(), 1);
                            }
                        } catch (java.lang.NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Mã sản phẩm phải là chữ số");
                            table.setValueAt(null, e.getLastRow(), 1);
                        }
                    } else if (e.getColumn() == 5) {
                        if (table.getValueAt(e.getLastRow(), 5) != null) {
                            try {
                                if (new Integer(table.getValueAt(e.getLastRow(), 5) + "") <= new Integer(table.getValueAt(e.getLastRow(), 4) + "")) {
                                    Double total = new Integer(table.getValueAt(e.getLastRow(), 5)+"") * (Double) (table.getValueAt(e.getLastRow(), 3));
                                    table.setValueAt(table.getRowCount(), e.getLastRow(), 0);
                                    table.setValueAt(total, e.getLastRow(), 6);
                                    tfTongtien.setText(calculateTotal());
                                    addRow();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Số lượng mua phải nhỏ hơn số lượng còn tồn kho");
                                    table.setValueAt(null, e.getLastRow(), 5);
                                }
                            } catch (java.lang.NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Số lượng phải là chữ số");
                                table.setValueAt(null, e.getLastRow(), 5);
                            }

                        }

                    }
                }
            }

        });
    }

    private String calculateTotal() {
        double total = 0;
        for (int i = 0; i < tableSanpham.getModel().getRowCount(); i++) {
            total += (Double) tableSanpham.getModel().getValueAt(i, 6);
        }

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        return formatter.format(total).toString();
    }

    private void addRow() {
        DefaultTableModel table = (DefaultTableModel) tableSanpham.getModel();
        Object[] emptyData = {null, null, null, null, null, null};
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
        jButton3 = new javax.swing.JButton();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Tồn kho", "Số lượng", "Tổng tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, false
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
            tableSanpham.getColumnModel().getColumn(0).setMaxWidth(50);
            tableSanpham.getColumnModel().getColumn(1).setMaxWidth(200);
            tableSanpham.getColumnModel().getColumn(3).setMinWidth(100);
            tableSanpham.getColumnModel().getColumn(3).setMaxWidth(100);
            tableSanpham.getColumnModel().getColumn(4).setMinWidth(100);
            tableSanpham.getColumnModel().getColumn(4).setMaxWidth(100);
            tableSanpham.getColumnModel().getColumn(5).setMinWidth(100);
            tableSanpham.getColumnModel().getColumn(5).setMaxWidth(100);
            tableSanpham.getColumnModel().getColumn(6).setMinWidth(100);
            tableSanpham.getColumnModel().getColumn(6).setMaxWidth(100);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
                .addComponent(tfTongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfTongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton3.setText("Tạo hóa đơn");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jButton3)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Dangnhap().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new NvbhMain(nv).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new NvbhTaoHoaDon(this.nv, this.tableSanpham.getModel()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NvbhBanhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NvbhBanhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NvbhBanhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NvbhBanhang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NhanVien nv = new NhanVien();
                nv.setTendangnhap("NHANVIEN001");
                nv.setId(3);
                new NvbhBanhang(nv).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTable tableSanpham;
    private javax.swing.JTextField tfTongtien;
    // End of variables declaration//GEN-END:variables
}
