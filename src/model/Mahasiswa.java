package model;

import exception.ValidationException;

//MODUL MASTER: MAHASISWA.
//PEMILIK: MAYANDA FEBRI YANTI
public class Mahasiswa extends BaseEntity {
    private String nim;
    private String nama;
    private String kelas;

    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String nama, String kelas) {
        this.nim= nim;
        this.nama = nama;
        this.kelas = kelas;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }


    @Override
    public void validasi() throws ValidationException {
        if (nim == null || nim.trim().isEmpty()) {
            throw new ValidationException("NIM", "tidak boleh kosong");
        }
        if (nim.trim().length() != 10) {
            throw new ValidationException("NIM", "harus 10 angka");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("Nama", "tidak boleh kosong");
        }
        if (kelas == null || kelas.trim().isEmpty()) {
            throw new ValidationException("Kelas", "tidak boleh kosong");
        }
    }

    @Override
    public String label() {
        return nama + " (" + nim + ")";
    }
}

