package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MahasiswaDao extends BaseDao<Mahasiswa> {

    @Override
    protected String namaTabel() {
        return "mahasiswa";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"nim", "nama", "kelas"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Mahasiswa p)
            throws SQLException{
        // urutan harus sama dengan di kolomData()
        ps.setString(1, p.getNim());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getKelas());
    }

    @Override
    protected  String kolomId() {
        return "mahasiswa_id";
    }

    @Override
    protected Mahasiswa dariBaris(ResultSet r) throws SQLException {
        Mahasiswa p = new Mahasiswa();
        p.setId(r.getInt("mahasiswa_id"));
        p.setNim(r.getString("nim"));
        p.setNama(r.getString("nama"));
        p.setKelas(r.getString("kelas"));
        return p;
    }

    public Mahasiswa cariNim(String nim) throws DataAccessException {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nim);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari mahasiswa", e);
        }
    }
}
