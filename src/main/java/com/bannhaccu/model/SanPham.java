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
public class SanPham {
    private int id;
    private String ten;
    private String thongsokithuat;
    private String mota;
    private int soluong;
    private double gia;
    private String mausac;
    private Hang hang;
    private TheLoai theloai;

    public SanPham() {
    }
    
    public SanPham(Integer id, String ten, String thongsokithuat, String mota, int soluong, float gia, String mausac, Hang hang, TheLoai theloai) {
        this.id = id;
        this.ten = ten;
        this.thongsokithuat = thongsokithuat;
        this.mota = mota;
        this.soluong = soluong;
        this.gia = gia;
        this.mausac = mausac;
        this.hang = hang;
        this.theloai = theloai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setThongsokithuat(String thongsokithuat) {
        this.thongsokithuat = thongsokithuat;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public void setHang(Hang hang) {
        this.hang = hang;
    }

    public void setTheloai(TheLoai theloai) {
        this.theloai = theloai;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getThongsokithuat() {
        return thongsokithuat;
    }

    public String getMota() {
        return mota;
    }

    public int getSoluong() {
        return soluong;
    }

    public double getGia() {
        return gia;
    }

    public String getMausac() {
        return mausac;
    }

    public Hang getHang() {
        return hang;
    }

    public TheLoai getTheloai() {
        return theloai;
    }
    
    
}
