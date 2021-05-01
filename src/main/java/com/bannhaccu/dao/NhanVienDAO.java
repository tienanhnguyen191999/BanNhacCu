/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.model.NhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
