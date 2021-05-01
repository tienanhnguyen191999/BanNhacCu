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
public class KhachHang extends User{
    private String maKH;

    public KhachHang() {
        super(null, null, null, null, null, null, null);
    }

    public KhachHang(String maKH, Integer id, String ten, String ngaysinh, String gioitinh, String email, String sodienthoai, String ghichu) {
        super(id, ten, ngaysinh, gioitinh, email, sodienthoai, ghichu);
        this.maKH = maKH;
    }
    
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
