package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Pengajuan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PengajuanDao extends BaseDao<Pengajuan> {

    @Override
    protected String namaTabel() {
        return "pengajuan";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"Mahasiswa_id", "JenisSurat_id", "TanggalAjukan",
                "Status", "TanggalSelesai", "Petugas_id"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Pengajuan p)
            throws SQLException {
        // Urutan di sini WAJIB sama dengan urutan di kolomData().
        ps.setInt(1, p.getMahasiswa_id());
        ps.setInt(2, p.getJenisSurat_id());
        ps.setDate(3, Date.valueOf(p.getTanggalAjukan()));
        ps.setString(4, p.getStatus());
        if (p.getTanggalSelesai() == null
                || p.getTanggalSelesai().trim().isEmpty()) {
            ps.setNull(5, Types.DATE);
        } else {
            ps.setDate(5, Date.valueOf(p.getTanggalSelesai()));
        }
        ps.setInt(6, p.getPetugas_id());
    }

    @Override
    protected Pengajuan dariBaris(ResultSet r) throws SQLException {
        Pengajuan p = new Pengajuan();
        p.setId(r.getInt("id"));
        p.setMahasiswa_id(r.getInt("Mahasiswa_id"));
        p.setJenisSurat_id(r.getInt("JenisSurat_id"));
        Date ajukan = r.getDate("TanggalAjukan");
        p.setTanggalAjukan(ajukan == null ? null : ajukan.toString());
        p.setStatus(r.getString("Status"));
        Date selesai = r.getDate("TanggalSelesai");
        p.setTanggalSelesai(selesai == null ? null : selesai.toString());
        p.setPetugas_id(r.getInt("Petugas_id"));
        return p;
    }

    /** Khas modul pengajuan - tidak diangkat ke BaseDao (lihat Keputusan 3). */
    public List<Pengajuan> cariStatus(String status) throws DataAccessException {
        String sql = "SELECT * FROM pengajuan WHERE Status = ? ORDER BY id";
        List<Pengajuan> hasil = new ArrayList<Pengajuan>();
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    hasil.add(dariBaris(r));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari pengajuan", e);
        }
        return hasil;
    }
}