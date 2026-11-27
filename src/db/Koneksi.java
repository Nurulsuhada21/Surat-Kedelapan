package db;

import exception.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/pengajuansurat"
            + "?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String PENGGUNA = "root";
    private static final String SANDI = "";
    private Koneksi() {
    }

    public static Connection buka() throws DataAccessException {
        try {
            return DriverManager.getConnection(URL, PENGGUNA, SANDI);
        } catch (SQLException e) {
            throw new DataAccessException("koneksi ke basis data gagal dibuka", e);
        }
    }
}
