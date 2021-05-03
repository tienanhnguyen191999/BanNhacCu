/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.model.NhanVien;
import com.bannhaccu.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author TienAnh
 */
public class NhanVienDAO extends DAO{
    
    public boolean kiemtraDangNhap (NhanVien nv) {
        try {
            // get data from table 'student'
            String sql = "select * from nhanvien where tendangnhap = ? and matkhau = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nv.getTendangnhap());
            stmt.setString(2, nv.getPassword());
            ResultSet rs = stmt.executeQuery();
            // show data
            while (rs.next()) {
                nv.setVitri(rs.getInt("vitri"));
                nv.setId(rs.getInt("id"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<NhanVien> getNhanvien () {
        ArrayList<NhanVien> arr_data = new ArrayList<NhanVien>();
        try {
            String sql = "SELECT * FROM nhanvien "
                + "JOIN user on user.id = nhanvien.id_user "
                + "ORDER BY nhanvien.id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("nhanvien.vitri"), 
                    rs.getString("nhanvien.tendangnhap"), 
                    "", 
                    rs.getInt("nhanvien.id"),  
                    rs.getString("user.ten"), 
                    rs.getString("user.ngaysinh"), 
                    rs.getString("user.gioitinh"), 
                    rs.getString("user.email"), 
                    rs.getString("user.sodienthoai"), 
                    rs.getString("user.ghichu"));
                arr_data.add(nv);
            }
            return arr_data;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
    
    public void storeNhanVien (NhanVien nv) {
        try {
            // Store user
            String sql = "INSERT INTO user (ten, sodienthoai) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nv.getTen());
            stmt.setString(2, nv.getSodienthoai());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            int id_user = 0;
            if (rs.next()){
                id_user = rs.getInt(1);
            }
            
            // Store nhan vien
            sql = "INSERT INTO nhanvien (id_user, tendangnhap, matkhau, vitri) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, id_user);
            stmt.setString(2, nv.getTendangnhap());
            stmt.setString(3, nv.getPassword());
            stmt.setInt(4, nv.getVitri());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkExsit(String username) {
        try {
            String sql = "SELECT * FROM nhanvien WHERE tendangnhap = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean xoaNhanVien (int id_nhanvien) {
        try {
            String sql = "SELECT * FROM nhanvien where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_nhanvien);
            ResultSet rs = stmt.executeQuery();
            int id_user = -1;
            if (rs.next()) {
                id_user = rs.getInt("id_user");
            }
            
            sql = "DELETE FROM user WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_user);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
