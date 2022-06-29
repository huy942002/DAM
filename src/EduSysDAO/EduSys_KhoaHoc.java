/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSysDAO;

import JDBCHelper.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhoaHoc;
import utils.*;

/**
 *
 * @author User
 */
public class EduSys_KhoaHoc extends DAO<KhoaHoc, String> {

    final String insert_SQL = "insert into KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao) values(?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "update KhoaHoc set MaCD=?, HocPhi = ?,ThoiLuong = ?,NgayKG =? where MaKH = ?";
    final String DELETE_SQL = "delete KhoaHoc where MaKH = ?";
    final String SELECT_ALL_SQL = "select * from KhoaHoc";
    final String SELECT_BY_ID_SQL = "select * from KhoaHoc WHERE MaKH = ?";
    final String SELECT_BY_MA_CD_SQL = "select * from KhoaHoc WHERE MaCD = ?";
   

    @Override
    public void insert(KhoaHoc entity) {
        jdbcHelper.update(insert_SQL,entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),
        entity.getNgayKG(),entity.getGhiChu(),entity.getMaNV(),entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        jdbcHelper.update(UPDATE_SQL ,entity.getMaCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getNgayKG(), entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL,id);
    }

    @Override
    public List<KhoaHoc> selecALL() {
         return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhoaHoc selectById(String key) {
        List<KhoaHoc> list = selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                KhoaHoc entity = new KhoaHoc();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaCD(rs.getString("MaCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setNgayKG(rs.getDate("NgayKG"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }
    public List<KhoaHoc> selectByChuyenDe(String macd) {
        return selectBySql(SELECT_BY_MA_CD_SQL,macd);
    }
    
    public List<Integer> SeletYears(){
        String sql = "SELECT DISTINCT year(NgayKG) Year FROM KhoaHoc ORDER BY Year DESC ";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.query(sql);
            while (rs.next()) {                
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
