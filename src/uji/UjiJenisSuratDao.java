package uji;

import dao.JenisSuratDao;
import exception.AppException;
import model.Jenis_surat;
import java.util.List;

public class UjiJenisSuratDao {
    public static void main(String[] args) {
        JenisSuratDao dao = new JenisSuratDao();
        try {
            Jenis_surat baru = new Jenis_surat("SR009", "Surat Rekomendasi",
                    "KTM", "2");
            dao.simpan(baru);
            System.out.println("[simpan ] id dari AUTO_INCREMENT = "
                    + baru.getId());
            Jenis_surat dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId ] " + dibaca.label());
            Jenis_surat lewatKode = dao.cariKode("SR009");
            System.out.println("[cariKode ] " + lewatKode.label());
            dibaca.setSyarat("KTM");
            System.out.println("[ubah ] berhasil? " + dao.ubah(dibaca));
            List<Jenis_surat> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (Jenis_surat p : semua) {
                System.out.println(" " + p.getId() + ". " + p.label());
            }
            System.out.println("[hapus ] berhasil? "
                    + dao.hapus(baru.getId()));
            System.out.println("[hapus ] id tak ada, berhasil? "
                    + dao.hapus(999999));
        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }
// data yang ditolak sebelum menyentuh basis data
        try {
            dao.simpan(new Jenis_surat("123", "Nik Pendek",
                    "08127000001", "Bengkalis"));
            System.out.println("[validasi ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi ] " + e.pesanPengguna());
        }
    }
}
