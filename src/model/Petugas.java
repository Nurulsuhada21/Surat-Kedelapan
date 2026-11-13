
package model;

import exception.ValidationException;
import model.BaseEntity;

// MODUL PETUGAS
public class Petugas extends BaseEntity {

    private String nama;
    private String jenisKelamin;
    private String telepon;
    private String alamat;

    // Constructor kosong
    public Petugas() {
    }

    // Constructor dengan parameter
    public Petugas(String nama, String jenisKelamin,
                   String telepon, String alamat) {
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.telepon = telepon;
        this.alamat = alamat;
    }

    // Getter dan Setter Nama
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter dan Setter Jenis Kelamin
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    // Getter dan Setter Telepon
    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    // Getter dan Setter Alamat
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Validasi data
    @Override
    public void validasi() throws ValidationException {

        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException(
                    "Nama", "tidak boleh kosong"
            );
        }

        if (jenisKelamin == null ||
                jenisKelamin.trim().isEmpty()) {
            throw new ValidationException(
                    "Jenis kelamin", "tidak boleh kosong"
            );
        }

        if (telepon == null || telepon.trim().isEmpty()) {
            throw new ValidationException(
                    "Telepon", "tidak boleh kosong"
            );
        }
    }

    // Label data
    @Override
    public String label() {
        return nama + " (" + telepon + ")";
    }
}
