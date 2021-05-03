/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.Hang;
import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.SanPham;
import com.bannhaccu.model.TheLoai;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            String sql = "select * from sanpham"
                + " join hang on sanpham.id_hang = hang.id"
                + " join theloai on sanpham.id_theloai = theloai.id"
                + " where sanpham.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            // show data
            while (rs.next()) {
                sp = new SanPham();
                sp.setId(rs.getInt("sanpham.id"));
                sp.setGia(rs.getInt("sanpham.gia"));
                sp.setSoluong(rs.getInt("sanpham.soluong"));
                sp.setTen(rs.getString("sanpham.ten"));
                sp.setThongsokithuat(rs.getString("sanpham.thongsokithuat"));
                sp.setMota(rs.getString("sanpham.mota"));
                sp.setMausac(rs.getString("sanpham.mausac"));

                Hang hang = new Hang(rs.getInt("hang.id"), rs.getString("hang.ten"), rs.getString("hang.mota"));
                sp.setHang(hang);

                TheLoai theloai = new TheLoai(rs.getInt("theloai.id"), rs.getString("theloai.ten"), rs.getString("theloai.mota"));
                sp.setTheloai(theloai);
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
                    stmt.setInt(1, new Integer(tm.getValueAt(i, 5) + ""));
                    stmt.setString(2, (String) tm.getValueAt(i, 1));
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateSoluong(TableModel tm) {
        for (int i = 0; i < tm.getRowCount(); i++) {
            try {
                if (tm.getValueAt(i, 1) != null) {
                    String sql = "UPDATE sanpham SET soluong=soluong + ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, new Integer(tm.getValueAt(i, 5) + ""));
                    stmt.setInt(2, (int) tm.getValueAt(i, 1));
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<SanPham> getSanpham() {
        ArrayList<SanPham> arr_data = new ArrayList<SanPham>();
        try {
            String sql = "SELECT * from sanpham"
                + " JOIN hang ON sanpham.id_hang = hang.id"
                + " JOIN theloai ON sanpham.id_theloai = theloai.id"
                + " ORDER BY sanpham.id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("sanpham.id"));
                sp.setGia(rs.getInt("sanpham.gia"));
                sp.setSoluong(rs.getInt("sanpham.soluong"));
                sp.setTen(rs.getString("sanpham.ten"));
                sp.setThongsokithuat(rs.getString("sanpham.thongsokithuat"));
                sp.setMota(rs.getString("sanpham.mota"));
                sp.setMausac(rs.getString("sanpham.mausac"));

                Hang hang = new Hang(rs.getInt("hang.id"), rs.getString("hang.ten"), rs.getString("hang.mota"));
                sp.setHang(hang);

                TheLoai theloai = new TheLoai(rs.getInt("theloai.id"), rs.getString("theloai.ten"), rs.getString("theloai.mota"));
                sp.setTheloai(theloai);

                arr_data.add(sp);
            }
            return arr_data;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }

    public boolean storeSanpham(SanPham sp) {
        try {
            String sql = "INSERT INTO sanpham (ten, soluong, thongsokithuat, mota, gia, id_theloai, id_hang, mausac) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, '')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sp.getTen());
            stmt.setInt(2, 0);
            stmt.setString(3, sp.getThongsokithuat());
            stmt.setString(4, sp.getMota());
            stmt.setDouble(5, sp.getGia());
            stmt.setInt(6, sp.getTheloai().getId());
            stmt.setInt(7, sp.getHang().getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean xoaSanPham (int id_sp) {
        try {
            String sql = "DELETE FROM sanpham WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_sp);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
