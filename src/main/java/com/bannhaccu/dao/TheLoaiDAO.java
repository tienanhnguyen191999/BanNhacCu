/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.TheLoai;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TienAnh
 */
public class TheLoaiDAO extends DAO{
    public ArrayList<TheLoai> gelAllTheLoai () {
        ArrayList<TheLoai> arr_data = new ArrayList<TheLoai>();
        try {
            String sql = "SELECT * FROM theloai";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TheLoai tl = new TheLoai(rs.getInt("id"), rs.getString("ten"), rs.getString("mota"));
                arr_data.add(tl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
}
