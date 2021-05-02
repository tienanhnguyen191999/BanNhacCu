/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.PhieuNhapKho;
import com.bannhaccu.model.SanPham;
import com.bannhaccu.model.SanPhamDuocNhapKho;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author TienAnh
 */
public class SanPhamDuocNhapKhoDAO extends DAO {

    public void storeSanPhamDuocNhapKho(TableModel tm, PhieuNhapKho pnk) {
        for (int i = 0; i < tm.getRowCount(); i++) {
            if (tm.getValueAt(i, 0) != null) {
                try {
                    // Get ID's Sanpham
                    String sql = "SELECT * FROM sanpham WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, (String) tm.getValueAt(i, 1));
                    ResultSet rs = stmt.executeQuery();
                    int id_sanpham = 0;
                    if (rs.next()) {
                        id_sanpham = rs.getInt(1);
                    }

                    // Store sản phẩm được nhập kho
                    sql = "INSERT INTO sanphamduocnhapkho (soluong, tongtien, id_sanpham, id_phieunhapkho) VALUES (?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    String thoigiantaohoadon = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
                    stmt.setString(1, (String) tm.getValueAt(i, 8));
                    stmt.setDouble(2, (double) tm.getValueAt(i, 9));
                    stmt.setInt(3, id_sanpham);
                    stmt.setInt(4, pnk.getId());
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(SanPhamDuocNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public ArrayList<SanPhamDuocNhapKho> getListSanPhamNhapKho (int id_pnk){
        System.out.println("id_pnk " + id_pnk);
        ArrayList<SanPhamDuocNhapKho> arr_data = new ArrayList<SanPhamDuocNhapKho>();
        try {
            String sql = "SELECT * FROM sanphamduocnhapkho "
                + "JOIN sanpham on sanphamduocnhapkho.id_sanpham = sanpham.id "
                + "WHERE sanphamduocnhapkho.id_phieunhapkho = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_pnk);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                double tongtien = rs.getDouble("sanphamduocnhapkho.tongtien");
                int soluong = rs.getInt("sanphamduocnhapkho.soluong");
                int id = rs.getInt("sanphamduocnhapkho.id");
                
                SanPham sanpham = new SanPham();
                sanpham.setTen(rs.getString("sanpham.ten"));
                sanpham.setId(rs.getInt("sanpham.id"));
                sanpham.setGia(rs.getDouble("sanpham.gia"));
                
                SanPhamDuocNhapKho spdnk = new SanPhamDuocNhapKho(id, soluong, "", tongtien, sanpham, null);
                arr_data.add(spdnk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDuocNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
}
