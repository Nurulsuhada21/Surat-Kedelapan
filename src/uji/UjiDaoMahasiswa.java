package uji;

import dao.MahasiswaDao;
import exception.AppException;
import model.Mahasiswa;

import java.util.List;

public class UjiDaoMahasiswa {
    public static void main(String[] args) {
        MahasiswaDao dao = new MahasiswaDao();

        try {
            Mahasiswa baru = new Mahasiswa("6304250015", "Mulhim",
                    "RPL 3C");
            dao.simpan(baru);
            System.out.println("[simpan    ] id baru dari AUTO_INCREMENT =  "
                    + baru.getId());

            Mahasiswa dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId    ] " + dibaca.label());

            Mahasiswa lewatNim = dao.cariNim("6304250015");
            System.out.println("[cariNim   ] " + lewatNim.label());

            dibaca.setKelas("RPL 3C");
            System.out.println("[ubah      ] berhasil?" + dao.ubah(dibaca));

            List<Mahasiswa> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (Mahasiswa p : semua) {
                System.out.println("             " + p.getId() + "." + p.label());
            }

            System.out.println("[hapus      ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus      ] id tidak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        // data yang ditolak sebelum menyentuh basis data
        try {
            dao.simpan(new Mahasiswa("630425", "Nim Pendek", "RPL 3C"));
            System.out.println("[valiasi ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi ] " + e.pesanPengguna());
        }
    }
}
