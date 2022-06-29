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
public class KhoaHoc {
    private int maKH,thoiLuong;
    private String maCD,ghiChu,maNV;
    private double hocPhi;
    private Date ngayKG,ngayTao;

    public KhoaHoc() {
    }

    public KhoaHoc(int maKH, int thoiLuong, String maCD, String ghiChu, String maNV, double hocPhi, Date ngayKG, Date ngayTao) {
        this.maKH = maKH;
        this.thoiLuong = thoiLuong;
        this.maCD = maCD;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.hocPhi = hocPhi;
        this.ngayKG = ngayKG;
        this.ngayTao = ngayTao;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(double hocPhi) {
        this.hocPhi = hocPhi;
    }

    public Date getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(Date ngayKG) {
        this.ngayKG = ngayKG;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return this.maCD;
    }
    
}
