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
import model.NguoiHoc;

/**
 *
 * @author User
 */
public class EduSys_NguoiHocDAO extends DAO<NguoiHoc, String> {

    final String insert_SQL = "insert into NguoiHoc values(?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaNH=?";
    final String DELETE_SQL = "delete from NguoiHoc where MaNH = ?";
    final String SELECT_ALL_SQL = "select * from NguoiHoc";
    final String SELECT_BY_ID_SQL = "select * from NguoiHoc WHERE MaNH = ?";
    final String SELECT_NOT_IN_COURSE_SQL = "select * from NguoiHoc WHERE HoTen like ? and MaNH not in (select MaNH from HocVien where MaKH=?)";

    @Override
    public void insert(NguoiHoc entity) {
        jdbcHelper.update(insert_SQL, entity.getMaNH(), entity.getHoTen(),
                entity.getNgaySinh(), entity.isGioiTinh(), entity.getDienThoai(),
                entity.getEmail(), entity.getGhichu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiHoc entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getNgaySinh(),
                entity.isGioiTinh(), entity.getDienThoai(), entity.getEmail(),
                entity.getGhichu(), entity.getMaNV(), entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.jdbcHelper.query(sql, args);
            while (rs.next()) {
                NguoiHoc entity = new NguoiHoc();
                entity.setMaNH(rs.getString("MaNH"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhichu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDK(rs.getDate("NgayDK"));
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<NguoiHoc> selecALL() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public List<NguoiHoc> selectNotlnCourse(int makh, String keyword) {
        String Sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ? AND MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH =?)";
        return selectBySql(Sql, "%" + keyword + "%", makh);
    }

    public List<NguoiHoc> selectByKework(String keyword) {
        String Sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return selectBySql(Sql, "%" + keyword + "%");
    }
}
