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
    private String thoigiantaophieu;
    private double tongtien;
    private int is_check;    
    private NhanVien nhanvien;


    public PhieuNhapKho(int id, String thoigiantaophieu, double tongtien, int is_check, NhanVien nhanvien) {
        this.id = id;
        this.thoigiantaophieu = thoigiantaophieu;
        this.tongtien = tongtien;
        this.is_check = is_check;
        this.nhanvien = nhanvien;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public double getTongtien() {
        return tongtien;
    }
    
    public int getId() {
        return id;
    }

    public String getThoigiantaophieu() {
        return thoigiantaophieu;
    }

    public NhanVien getNhanvien() {
        return nhanvien;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThoigiantaophieu(String thoigiantaophieu) {
        this.thoigiantaophieu = thoigiantaophieu;
    }

    public void setNhanvien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }
    
    
}
