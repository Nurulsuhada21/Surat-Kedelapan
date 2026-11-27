package uji;

import dao.PengajuanDao;
import exception.AppException;
import model.Pengajuan;

import java.util.List;

public class UjiDaoPengajuan {

    public static void main(String[] args) {
        PengajuanDao dao = new PengajuanDao();

        try {
            Pengajuan baru = new Pengajuan(1, 1, "2026-11-23", "Diajukan", "", 1);
            dao.simpan(baru);
            System.out.println("[simpan    ] id dari AUTO_INCREMENT = "
                    + baru.getId());

            Pengajuan dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId    ] " + dibaca.label());

            List<Pengajuan> diajukan = dao.cariStatus("Diajukan");
            System.out.println("[cariStatus] jumlah baris = " + diajukan.size());

            dibaca.setStatus("Selesai");
            System.out.println("[ubah      ] berhasil? " + dao.ubah(dibaca));

            List<Pengajuan> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (Pengajuan p : semua) {
                System.out.println("             " + p.getId() + ". " + p.label());
            }

            System.out.println("[hapus     ] berhasil? "
                    + dao.hapus(baru.getId()));
            System.out.println("[hapus     ] id tak ada, berhasil? "
                    + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            // baris diagnosa sementara: menampilkan penyebab asli dari MySQL
            Throwable sebab = e.getCause();
            while (sebab != null) {
                System.out.println("PENYEBAB ASLI: " + sebab);
                sebab = sebab.getCause();
            }
        }

        // data yang ditolak sebelum menyentuh basis data
        try {
            dao.simpan(new Pengajuan(0, 1, "2026-11-23", "Diajukan", "", 1));
            System.out.println("[validasi  ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi  ] " + e.pesanPengguna());
        }
    }
}