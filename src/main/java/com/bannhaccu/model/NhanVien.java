/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.model;


public class NhanVien extends User{
    private int vitri;
    private String tendangnhap;
    private String password;

    public NhanVien() {
        super(null, null, null, null, null, null, null);
    }
    
    public NhanVien(int vitri, String tendangnhap, String password, Integer id, String ten, String ngaysinh, String gioitinh, String email, String sodienthoai, String ghichu) {
        super(id, ten, ngaysinh, gioitinh, email, sodienthoai, ghichu);
        this.vitri = vitri;
        this.tendangnhap = tendangnhap;
        this.password = password;
    }

    public int getVitri() {
        return vitri;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public String getPassword() {
        return password;
    }

    public void setVitri(int vitri) {
        this.vitri = vitri;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
