/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bannhaccu.model;


public class TheLoai {
    private Integer id;
    private String ten;
    private String mota;

    public TheLoai(Integer id, String ten, String mota) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
    }

    public Integer getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getMota() {
        return mota;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
