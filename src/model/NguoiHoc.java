/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author User
 */
public class NguoiHoc {
    private String maNH,hoTen,dienThoai,email,ghichu,maNV;
    private Date ngaySinh,ngayDK;
    private boolean GioiTinh;

    public NguoiHoc() {
    }

    public NguoiHoc(String maNH, String hoTen, String dienThoai, String email, String ghichu, String maNV, Date ngaySinh, Date ngayDK, boolean GioiTinh) {
        this.maNH = maNH;
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.email = email;
        this.ghichu = ghichu;
        this.maNV = maNV;
        this.ngaySinh = ngaySinh;
        this.ngayDK = ngayDK;
        this.GioiTinh = GioiTinh;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(Date ngayDK) {
        this.ngayDK = ngayDK;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }
    
    
}
