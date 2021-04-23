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
public class User {
    private Integer id;
    private String ten;
    private String ngaysinh;
    private String gioitinh;
    private String email;
    private String sodienthoai;
    private String ghichu;

    public User(Integer id, String ten, String ngaysinh, String gioitinh, String email, String sodienthoai, String ghichu) {
        this.id = id;
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.email = email;
        this.sodienthoai = sodienthoai;
        this.ghichu = ghichu;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
    
    

    public Integer getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getEmail() {
        return email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getGhichu() {
        return ghichu;
    }
}
