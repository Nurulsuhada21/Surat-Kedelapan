package uji;

import db.Koneksi;
import exception.AppException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class UjiKoneksi {
    public static void main(String[] args) {
        try (Connection c = Koneksi.buka()) {
            DatabaseMetaData info = c.getMetaData();
            System.out.println("Koneksi berhasil.");
            System.out.println("Server : " + info.getDatabaseProductName()
                    + " " + info.getDatabaseProductVersion());
            System.out.println("Driver : " + info.getDriverName()
                    + " " + info.getDriverVersion());

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            if (e.getCause() != null) {
                System.out.println("Rincian teknis: " + e.getCause().getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal ditutup: " + e.getMessage());
        }
    }
}
