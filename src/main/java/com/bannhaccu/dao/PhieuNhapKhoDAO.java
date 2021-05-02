/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.model.HoaDon;
import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.PhieuNhapKho;
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
public class PhieuNhapKhoDAO extends DAO {
    public PhieuNhapKho storePhieuNhapKho (double total, NhanVien nv) {
        PhieuNhapKho pnk = null;
        try {
            String sql = "INSERT INTO phieunhapkho (thoigiantaophieu, tongtien, id_nhanvien, is_check) VALUES (?, ?, ?, false)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            String thoigiantaohoadon = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            stmt.setString(1, thoigiantaohoadon);
            stmt.setDouble(2, total);
            stmt.setInt(3, nv.getId());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            int id_pnk = 0;
            if (rs.next()) id_pnk = rs.getInt(1);
            pnk = new PhieuNhapKho(id_pnk, thoigiantaohoadon, total, 0, nv);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return pnk;
    }
    
    public ArrayList<PhieuNhapKho> getListPhieuNhapKho () {
        ArrayList<PhieuNhapKho> arr_data = new ArrayList<PhieuNhapKho>();
        try {
            String sql = "SELECT * FROM phieunhapkho "
                       + "JOIN nhanvien on phieunhapkho.id_nhanvien = nhanvien.id "
                       + "ORDER BY phieunhapkho.id DESC ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("phieunhapkho.id");
                String thoigiantaophieu = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(rs.getTimestamp("phieunhapkho.thoigiantaophieu"));
                double tongtien = rs.getDouble("phieunhapkho.tongtien");
                int is_check = rs.getInt("phieunhapkho.is_check");
                
                NhanVien nhanvien = new NhanVien();
                nhanvien.setTendangnhap(rs.getString("nhanvien.tendangnhap"));
                nhanvien.setVitri(rs.getInt("nhanvien.vitri"));
                PhieuNhapKho pnk = new PhieuNhapKho(id, thoigiantaophieu, tongtien, is_check, nhanvien);
                arr_data.add(pnk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
    
    public void xoaPhieuNhapKho (int id) {
        try {
            String sql = "DELETE FROM phieunhapkho WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public ArrayList<PhieuNhapKho> getListPhieuNhapKhoNotCheck () {
        ArrayList<PhieuNhapKho> arr_data = new ArrayList<PhieuNhapKho>();
        try {
            String sql = "SELECT * FROM phieunhapkho "
                       + "JOIN nhanvien on phieunhapkho.id_nhanvien = nhanvien.id "
                       + "WHERE phieunhapkho.is_check = 0 "
                       + "ORDER BY phieunhapkho.id DESC ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("phieunhapkho.id");
                String thoigiantaophieu = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(rs.getTimestamp("phieunhapkho.thoigiantaophieu"));
                double tongtien = rs.getDouble("phieunhapkho.tongtien");
                int is_check = rs.getInt("phieunhapkho.is_check");
                
                NhanVien nhanvien = new NhanVien();
                nhanvien.setTendangnhap(rs.getString("nhanvien.tendangnhap"));
                nhanvien.setVitri(rs.getInt("nhanvien.vitri"));
                PhieuNhapKho pnk = new PhieuNhapKho(id, thoigiantaophieu, tongtien, is_check, nhanvien);
                arr_data.add(pnk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
    
    public void updateIsCheck (int id_pnk) {
        try {
            String sql = "UPDATE phieunhapkho "
                + "SET is_check = 1 WHERE id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_pnk);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
