package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Jenis_surat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JenisSuratDao extends BaseDao<Jenis_surat> {
    @Override
    protected String namaTabel() {
        return "jenis_surat";
    }
    @Override
    protected String[] kolomData() {
        return new String[]{ "kode", "nama", "syarat", "lama_proses_hari"};
    }
    @Override
    protected void isiParameter(PreparedStatement ps, Jenis_surat p)
            throws SQLException {
// Urutan di sini WAJIB sama dengan urutan di kolomData().
        ps.setString(1, p.getKode());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getSyarat());
        ps.setString(4, p.getLama_proses_hari());
    }
    @Override
    protected Jenis_surat dariBaris(ResultSet r) throws SQLException {
        Jenis_surat p = new Jenis_surat();
        p.setId(r.getInt("jenisSurat_id"));
        p.setKode(r.getString("kode"));
        p.setNama(r.getString("nama"));
        p.setSyarat(r.getString("syarat"));
        p.setLama_proses_hari(r.getString("lama_proses_hari"));
        return p;
    }
    @Override
    protected String kolomId() {
        return "jenisSurat_id";
    }
    /** Khas modul penumpang - tidak diangkat ke BaseDao (lihat Keputusan 3). */
    public Jenis_surat cariKode(String kode) throws DataAccessException {
        String sql = "SELECT * FROM jenis_surat WHERE kode = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kode);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari surat", e);
        }
    }
}
