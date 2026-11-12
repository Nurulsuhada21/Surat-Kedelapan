package model;

import exception.ValidationException;
public class Jenis_surat extends BaseEntity{
    private String kode;
    private String nama;
    private String syarat;
    private String lama_proses_hari;

    public Jenis_surat(){
    }
    public Jenis_surat(String kode, String nama, String syarat, String lama_proses_hari){
        this.kode=kode;
        this.nama=nama;
        this.syarat=syarat;
        this.lama_proses_hari=lama_proses_hari;
    }
    public String getKode(){return kode;}
    public void setKode(String kode){this.kode=kode;}

    public String getNama(){return nama;}
    public void setNama(String nama){this.nama=nama;}

    public String getSyarat(){return syarat;}
    public void setSyarat(String syarat){this.syarat=syarat;}

    public String getLama_proses_hari(){return lama_proses_hari;}
    public void setLama_proses_hari(String lama_proses_hari){this.lama_proses_hari=lama_proses_hari;}

    @Override
    public void validasi() throws ValidationException{

        if (kode==null || kode.trim().isEmpty()){
            throw new ValidationException(" Kode ", " tidak boleh kosong");
        }
        if (!kode.matches("\\d{16}")){
            throw new ValidationException(" Kode ", " harus 5 karakter");
        }
        if (nama==null || nama.trim().isEmpty()){
            throw new ValidationException(" nama ", " tidak boleh kosong");
        }
    }
    @Override
    public String label(){
        return  "(" + kode + ")" + nama;
    }
}
