/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.KhachHang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TienAnh
 */
public class KhachHangDAO extends DAO {

    public KhachHang createOrUpdateKhachHang(String sdt, String name) {
        KhachHang kh = null;
        try {
            String sql = "SELECT * FROM user "
                + "INNER JOIN khachhang on user.id = khachhang.id_user "
                + "WHERE user.sodienthoai = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            kh = new KhachHang();

            if (rs.isBeforeFirst()) {
                if (rs.next()) {
                    kh.setId(rs.getInt("khachhang.id"));
                    kh.setMaKH(rs.getString("makh"));
                    kh.setTen(rs.getString("ten"));
                    kh.setSodienthoai(sdt);
                }
            } else {
                // Store user
                sql = "INSERT INTO user (ten, sodienthoai) VALUES (?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, name);
                stmt.setString(2, sdt);
                stmt.executeUpdate();
                ResultSet row = stmt.getGeneratedKeys();

                int id_user = 0;
                if (row.next()){
                    id_user = row.getInt(1);
                }
                
                // Store KhachHang
                sql = "INSERT INTO khachhang (makh, id_user) VALUES (?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, "KH" + id_user);
                stmt.setInt(2, id_user);
                stmt.executeUpdate();

                rs = stmt.getGeneratedKeys();
                int id_kh = 0;
                if (rs.next()){
                    id_kh = rs.getInt(1);
                }
                kh.setId(id_kh);
                kh.setMaKH("KH"+id_user);
                kh.setTen(name);
                kh.setSodienthoai(sdt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kh;
    }
}
