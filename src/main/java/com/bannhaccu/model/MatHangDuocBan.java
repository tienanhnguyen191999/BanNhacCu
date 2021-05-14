/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.model;


public class MatHangDuocBan {
    private int id;
    private int soluong;
    private Double tongtien;
    private String mota;
    private HoaDon hoadon;
    private SanPham sanpham;

    public MatHangDuocBan(int id, int soluong, Double tongtien, String mota, HoaDon hoadon, SanPham sanpham) {
        this.id = id;
        this.soluong = soluong;
        this.tongtien = tongtien;
        this.mota = mota;
        this.hoadon = hoadon;
        this.sanpham = sanpham;
    }

    public int getId() {
        return id;
    }

    public int getSoluong() {
        return soluong;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public String getMota() {
        return mota;
    }

    public HoaDon getHoadon() {
        return hoadon;
    }

    public SanPham getSanpham() {
        return sanpham;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setHoadon(HoaDon hoadon) {
        this.hoadon = hoadon;
    }

    public void setSanpham(SanPham sanpham) {
        this.sanpham = sanpham;
    }
    
    
}
