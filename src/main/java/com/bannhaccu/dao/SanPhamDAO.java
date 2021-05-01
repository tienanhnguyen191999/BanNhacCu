/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.SanPham;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author TienAnh
 */
public class SanPhamDAO extends DAO {

    public SanPham getSanphamByID(int id) {
        SanPham sp = null;
        try {
            // get data from table 'student'
            String sql = "select * from sanpham where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            // show data
            while (rs.next()) {
                sp = new SanPham();
                sp.setId(rs.getInt("id"));
                sp.setGia(rs.getInt("gia"));
                sp.setSoluong(rs.getInt("soluong"));
                sp.setTen(rs.getString("ten"));
                sp.setThongsokithuat(rs.getString("thongsokithuat"));
                sp.setMota(rs.getString("mota"));
                sp.setMausac(rs.getString("mausac"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sp;
    }

    public void capNhatSoLuongSanPham(TableModel tm) {
        for (int i = 0; i < tm.getRowCount(); i++) {
            try {
                if (tm.getValueAt(i, 1) != null) {
                    String sql = "UPDATE sanpham SET soluong=soluong - ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, new Integer(tm.getValueAt(i, 5)+""));
                    stmt.setString(2, (String) tm.getValueAt(i, 1));
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
