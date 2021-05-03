/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.HoaDon;
import com.bannhaccu.model.MatHangDuocBan;
import com.bannhaccu.model.SanPham;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author TienAnh
 */
public class MatHangDuocBanDAO extends DAO{
    public void storeHangDuocBan(TableModel tm, HoaDon hoadon) {
        for (int i = 0; i < tm.getRowCount(); i++) {
            try {
                if (tm.getValueAt(i, 1) != null) {
                    // Get ID's Sanpham
                    String sql = "SELECT * FROM sanpham WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, (String) tm.getValueAt(i, 1));
                    ResultSet rs = stmt.executeQuery();
                    int id_sanpham = 0;
                    if (rs.next()){
                        id_sanpham = rs.getInt(1);
                    }

                    // Store mathangduocban
                    sql = "INSERT INTO mathangduocchon (soluong, tongtien, id_hoadon, id_sanpham) VALUES (?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, new Integer(tm.getValueAt(i, 5)+""));
                    stmt.setDouble(2, (double) tm.getValueAt(i, 6));
                    stmt.setInt(3, hoadon.getId());
                    stmt.setInt(4, id_sanpham);
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MatHangDuocBanDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<MatHangDuocBan> getMatHangDuocBanByIdHoaDon (int id_hoadon) {
        ArrayList<MatHangDuocBan> arr_data = new ArrayList<MatHangDuocBan>();
        try {
            String sql = "SELECT * FROM mathangduocchon "
                       + "JOIN sanpham on mathangduocchon.id_sanpham = sanpham.id "
                       + "WHERE id_hoadon = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_hoadon);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){ 
                SanPham sanpham = new SanPham(
                    rs.getInt("sanpham.id"),
                    rs.getString("sanpham.ten"),
                    rs.getString("sanpham.thongsokithuat"),
                    rs.getString("sanpham.mota"),
                    rs.getInt("sanpham.soluong"), 
                    (float) rs.getDouble("sanpham.gia"),
                    rs.getString("sanpham.mausac"), 
                    null, null);
                
                MatHangDuocBan mhdb = new MatHangDuocBan(
                    rs.getInt("mathangduocchon.id"), 
                    rs.getInt("mathangduocchon.soluong"), 
                    rs.getDouble("mathangduocchon.tongtien"),
                    "", null, 
                    sanpham);
                arr_data.add(mhdb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatHangDuocBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
}
