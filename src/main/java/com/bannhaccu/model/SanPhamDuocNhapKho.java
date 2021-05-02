/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.model;

/**
 *
 * @author TienAnh
 */
public class SanPhamDuocNhapKho {

    private int id;
    private int soluong;
    private String mota;
    private double tongtien;
    private SanPham sanpham;
    private PhieuNhapKho phieunhapkho;

    public SanPhamDuocNhapKho(int id, int soluong, String mota, double tongtien, SanPham sanpham, PhieuNhapKho phieunhapkho) {
        this.id = id;
        this.soluong = soluong;
        this.mota = mota;
        this.tongtien = tongtien;
        this.sanpham = sanpham;
        this.phieunhapkho = phieunhapkho;
    }
    
    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setSanpham(SanPham sanpham) {
        this.sanpham = sanpham;
    }

    public void setPhieunhapkho(PhieuNhapKho phieunhapkho) {
        this.phieunhapkho = phieunhapkho;
    }

    public int getId() {
        return id;
    }

    public int getSoluong() {
        return soluong;
    }

    public String getMota() {
        return mota;
    }

    public SanPham getSanpham() {
        return sanpham;
    }

    public PhieuNhapKho getPhieunhapkho() {
        return phieunhapkho;
    }

}
