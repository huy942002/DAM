/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSysDAO;

import JDBCHelper.jdbcHelper;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;

/**
 *
 * @author User
 */
public class EduSys_NhanVienDAO extends DAO<NhanVien, String>{
    Connection conn;
    final String insert_SQL = "insert into NhanVien values(?,?,?,?)";
    final String UPDATE_SQL = "update NhanVien set MatKhau = ?,Hoten = ?,VaiTro = ?\n" +
"where MaNV = ?";
    final String DELETE_SQL = "delete from NhanVien where MaNV = ?";
    final String SELECT_ALL_SQL = "select * from NhanVien";
    final String SELECT_BY_ID_SQL = "select * from NhanVien WHERE MaNV = ?";
    

    @Override
    public List<NhanVien> selecALL() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
       List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
       
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
       List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString(1));
                entity.setMatKhau(rs.getString(2));
                entity.setHovaTen(rs.getString(3));
                entity.setVaiTro(rs.getBoolean(4));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
       
    }

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(insert_SQL, entity.getMaNV(),entity.getMatKhau(),entity.getHovaTen(),entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getMatKhau(),entity.getHovaTen(),entity.isVaiTro(),entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL,id);
    }
    
    
}
