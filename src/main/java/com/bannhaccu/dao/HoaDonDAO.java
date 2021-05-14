/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.dao;

import static com.bannhaccu.dao.DAO.conn;
import com.bannhaccu.model.HoaDon;
import com.bannhaccu.model.KhachHang;
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


public class HoaDonDAO extends DAO{
    public HoaDon storeHoadon (Double total, KhachHang kh, NhanVien nv) {
        HoaDon hd = null;
        try {
            String sql = "INSERT INTO hoadon (thoigiantaohoadon, tongtien, id_nhanvien, id_khachhang) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            String thoigiantaohoadon = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            stmt.setString(1, thoigiantaohoadon);
            stmt.setDouble(2, total);
            stmt.setInt(3, nv.getId());
            stmt.setDouble(4, kh.getId());
            stmt.executeUpdate();
            
            
            ResultSet rs = stmt.getGeneratedKeys();
            int id_hd = 0;
            if (rs.next()){
                id_hd = rs.getInt(1);
            }
            hd = new HoaDon(id_hd, thoigiantaohoadon, total, nv, kh);
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hd;
    }
    
    public ArrayList<HoaDon> getHoadDonInRangeDate (Date startDate, Date endDate) {
        ArrayList<HoaDon> arr_data = new ArrayList<HoaDon>();
        try {
            String sql = "SELECT * FROM hoadon "
                       + "WHERE thoigiantaohoadon BETWEEN ? AND ?"
                       + "ORDER BY thoigiantaohoadon DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            startDate.setHours(0); startDate.setMinutes(0); startDate.setSeconds(0);
            endDate.setHours(23); endDate.setMinutes(59); endDate.setSeconds(59);
            String startDateStr = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(startDate);
            String endDateStr = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(endDate);
            stmt.setString(1, startDateStr);
            stmt.setString(2, endDateStr);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("id"), 
                    new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(rs.getTimestamp("thoigiantaohoadon")), 
                    rs.getDouble("tongtien"), 
                    null,
                    null
                );
                arr_data.add(hd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr_data;
    }
}
