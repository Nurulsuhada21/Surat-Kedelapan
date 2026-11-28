package uji;

import dao.PetugasDao;
import exception.AppException;
import model.Petugas;

import java.util.List;

public class UjiDaoPetugas {
    public static void main(String[] args) {
        PetugasDao dao = new PetugasDao();
        try {
            Petugas baru = new Petugas("Andi Pratama", "Laki-laki", "081234567890", "Jl. Sudirman, Bengkalis");
            dao.simpan(baru);
            System.out.println("[simpan    ] id dari AUTO_INCREMENT = " + baru.getId());

            Petugas dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId    ] " + dibaca.label());

            Petugas lewatNama = dao.cariNama("Andi Pratama");
            System.out.println("[cariNama  ] " + lewatNama.label());

            dibaca.setTelepon("089988776655");
            System.out.println("[ubah      ] berhasil? " + dao.ubah(dibaca));

            List<Petugas> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (Petugas p : semua) {
                System.out.println("  " + p.getId() + ". " + p.label());
            }

            System.out.println("[hapus     ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus     ] id tak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        try {
            dao.simpan(new Petugas("", "Laki-laki", "081234567890", "Bengkalis"));
            System.out.println("[validasi  ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi  ] " + e.pesanPengguna());
        }
    }
}
