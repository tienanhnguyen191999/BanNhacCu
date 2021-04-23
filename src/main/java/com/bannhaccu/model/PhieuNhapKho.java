/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.model;

import java.util.Date;

/**
 *
 * @author TienAnh
 */
public class PhieuNhapKho {
    private int id;
    private Date thoigiantaophieu;
    private NhanVien nhanvien;

    public PhieuNhapKho(int id, Date thoigiantaophieu, NhanVien nhanvien) {
        this.id = id;
        this.thoigiantaophieu = thoigiantaophieu;
        this.nhanvien = nhanvien;
    }
    
    public int getId() {
        return id;
    }

    public Date getThoigiantaophieu() {
        return thoigiantaophieu;
    }

    public NhanVien getNhanvien() {
        return nhanvien;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThoigiantaophieu(Date thoigiantaophieu) {
        this.thoigiantaophieu = thoigiantaophieu;
    }

    public void setNhanvien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }
    
    
}
