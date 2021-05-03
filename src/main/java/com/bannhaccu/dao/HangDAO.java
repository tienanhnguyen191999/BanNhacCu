/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import com.bannhaccu.model.Hang;
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
public class HangDAO extends DAO{
    public ArrayList<Hang> gelAllHang () {
        ArrayList<Hang> arr_data = new ArrayList<Hang>();
        try {
            String sql = "SELECT * FROM hang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Hang hang = new Hang(rs.getInt("id"), rs.getString("ten"), rs.getString("mota"));
                arr_data.add(hang);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
}
