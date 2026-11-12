package model;

import exception.ValidationException;

/** MODUL TRANSAKSI: PENGAJUAN.PEMILIK NURUL SUHADA */
public class Pengajuan extends BaseEntity {

    private int Mahasiswa_id;
    private int JenisSurat_id;
    private String TanggalAjukan;
    private String Status;
    private String TanggalSelesai;
    private int Petugas_id;

    public Pengajuan() {
    }

    public Pengajuan(int Mahasiswa_id, int JenisSurat_id, String TanggalAjukan, String Status, String TanggalSelesai, int Petugas_id) {
        this.Mahasiswa_id = Mahasiswa_id;
        this.JenisSurat_id = JenisSurat_id;
        this.TanggalAjukan = TanggalAjukan;
        this.Status = Status;
        this.TanggalSelesai = TanggalSelesai;
        this.Petugas_id = Petugas_id;
    }

    public int getMahasiswa_id() { return Mahasiswa_id; }
    public void setMahasiswa_id(int Mahasiswa_id) { this.Mahasiswa_id =Mahasiswa_id; }

    public int getJenisSurat_id () { return JenisSurat_id ; }
    public void setJenisSurat_id (int JenisSurat_id ) { this.JenisSurat_id  = JenisSurat_id ; }

    public String getTanggalAjukan() { return TanggalAjukan; }
    public void setTanggalAjukan(String TanggalAjukan) { this.TanggalAjukan = TanggalAjukan; }

    public String getStatus() { return Status; }
    public void setStatus(String Status) { this.Status = Status; }

    public String getTanggalSelesai() { return TanggalSelesai; }
    public void setTanggalSelesai(String TanggalSelesai) { this.TanggalSelesai = TanggalSelesai; }

    public int getPetugas_id() { return Petugas_id; }
    public void setPetugas_id(int Petugas_id) { this.Petugas_id = Petugas_id; }

    @Override
    public void validasi() throws ValidationException {
        if (Mahasiswa_id == 0) {
            throw new ValidationException("Mahasiswa_id", "tidak boleh 0");
        }
        if (JenisSurat_id == 0) {
            throw new ValidationException("Jenis Surat_id", "tidak boleh 0");
        }
        if (TanggalAjukan == null || TanggalAjukan.trim().isEmpty()) {
            throw new ValidationException("tanggal ajukan", "tidak boleh kosong");
        }
        if (Status == null || Status.trim().isEmpty()) {
            throw new ValidationException("Status", "tidak boleh kosong");
        }
        if (Petugas_id == 0) {
            throw new ValidationException("Petugas_id", "tidak boleh 0");
        }
    }

    @Override
    public String label() {
        return "pengajuan surat-" + Mahasiswa_id + "("+ Status + ")";
    }
}
