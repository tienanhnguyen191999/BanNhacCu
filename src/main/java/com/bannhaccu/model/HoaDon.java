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
public class HoaDon {
    private Integer id;
    private Date thoigiantaohoadon;
    private Double tongtien;
    private NhanVien nhanvien;
    private KhachHang khachhang;

    public HoaDon(Integer id, Date thoigiantaohoadon, Double tongtien, NhanVien nhanvien, KhachHang khachhang) {
        this.id = id;
        this.thoigiantaohoadon = thoigiantaohoadon;
        this.tongtien = tongtien;
        this.nhanvien = nhanvien;
        this.khachhang = khachhang;
    }
    
    public Integer getId() {
        return id;
    }

    public Date getThoigiantaohoadon() {
        return thoigiantaohoadon;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public NhanVien getNhanvien() {
        return nhanvien;
    }

    public KhachHang getKhachhang() {
        return khachhang;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setThoigiantaohoadon(Date thoigiantaohoadon) {
        this.thoigiantaohoadon = thoigiantaohoadon;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public void setNhanvien(NhanVien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public void setKhachhang(KhachHang khachhang) {
        this.khachhang = khachhang;
    }
    
    
}