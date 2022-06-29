/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSysDAO;

import JDBCHelper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.HocVien;

/**
 *
 * @author User
 */
public class EduSys_HocVien extends DAO<HocVien, Integer> {

    final String insert_SQL = "insert into HocVien values(?,?,?)";
    final String UPDATE_SQL = "update HocVien set MaKH = ?,MaNH = ?,Diem = ?\n"
            + "where MaHV = ?";
    final String DELETE_SQL = "delete from HocVien where MaHV = ?";
    final String SELECT_ALL_SQL = "select * from HocVien";
    final String SELECT_BY_ID_SQL = "select * from HocVien WHERE MaHV = ?";

    @Override
    public void insert(HocVien entity) {
        jdbcHelper.update(insert_SQL,entity.getMaKH(),entity.getMaNH(),entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
         jdbcHelper.update(UPDATE_SQL,entity.getMaKH(),entity.getMaNH(),entity.getDiem(),entity.getMaHV());
    }

    @Override
    public List<HocVien> selecALL() {
     return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getDouble("Diem"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }
    public List<HocVien> selectByKhoaHoc(int makh){
    String sql = "SELECT * FROM HocVien WHERE MaKH =?";
    return this.selectBySql(sql,makh);
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HocVien selectById(Integer key) {
        List<HocVien> list = selectBySql(SELECT_BY_ID_SQL, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
