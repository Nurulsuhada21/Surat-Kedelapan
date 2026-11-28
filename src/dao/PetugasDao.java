package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Petugas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetugasDao extends BaseDao<Petugas> {

    @Override
    protected String namaTabel() {
        return "petugas";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"nama", "jenis_kelamin", "telepon", "alamat"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Petugas p) throws SQLException {
        ps.setString(1, p.getNama());
        ps.setString(2, p.getJenisKelamin());
        ps.setString(3, p.getTelepon());
        ps.setString(4, p.getAlamat());
    }

    @Override
    protected String kolomId() {
        return "petugas_id";
    }

    @Override
    protected Petugas dariBaris(ResultSet r) throws SQLException {
        Petugas p = new Petugas();
        p.setId(r.getInt(kolomId()));
        p.setNama(r.getString("nama"));
        p.setJenisKelamin(r.getString("jenis_kelamin"));
        p.setTelepon(r.getString("telepon"));
        p.setAlamat(r.getString("alamat"));
        return p;
    }

    public Petugas cariNama(String nama) throws DataAccessException {
        String sql = "SELECT * FROM " + namaTabel() + " WHERE nama = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari petugas", e);
        }
    }
}
