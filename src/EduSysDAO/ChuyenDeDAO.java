/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSysDAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ChuyenDe;
import JDBCHelper.jdbcHelper;

/**
 *
 * @author User
 */
public class ChuyenDeDAO extends DAO<ChuyenDe, String> {

    final String insert_SQL = "INSERT INTO ChuyenDe VALUES (?, ?, ?, ?, ?, ?)";
    final String UPDATE_SQL = "update ChuyenDe set TenCD = ?,HocPhi =? ,ThoiLuong =?,Hinh=?,MoTa=?\n"
            + "where MaCD =?";
    final String DELETE_SQL = "delete from ChuyenDe where MaCD = ?";
    final String SELECT_ALL_SQL = "select * from ChuyenDe";
    final String SELECT_BY_ID_SQL = "select * from ChuyenDe WHERE MaCD = ?";

    @Override
    public void insert(ChuyenDe entity) {
        jdbcHelper.update(insert_SQL, entity.getMaCD(),entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        jdbcHelper.update(UPDATE_SQL,entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa(),entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL,id);
    }

    @Override
    public List<ChuyenDe> selecALL() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectById(String key) {
        List<ChuyenDe> list = selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ChuyenDe entity = new ChuyenDe();
                entity.setMaCD(rs.getString("MaCD"));
                entity.setTenCD(rs.getString("TenCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMoTa(rs.getString("MoTa"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
