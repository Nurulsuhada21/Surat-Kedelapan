package gui;

import dao.PengajuanDao;
import exception.AppException;
import exception.ValidationException;
import model.Pengajuan;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormPengajuan extends BaseForm {

    private final PengajuanDao dao = new PengajuanDao();

    // JANGAN diberi nilai awal di sini.
    private JTextField txtMahasiswa_id;
    private JTextField txtJenisSurat_id;
    private JTextField txtTanggalAjukan;
    private JTextField txtStatus;
    private JTextField txtTanggalSelesai;
    private JTextField txtPetugas_id;

    private GenericTableModel<Pengajuan> model;
    private JTable tabel;
    private int idTerpilih;

    public FormPengajuan() {
        super("Data Pengajuan Surat", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtMahasiswa_id = new JTextField();
        txtJenisSurat_id = new JTextField();
        txtTanggalAjukan = new JTextField();
        txtStatus = new JTextField();
        txtTanggalSelesai = new JTextField();
        txtPetugas_id = new JTextField();

        String[] label = {"Mahasiswa_id", "JenisSurat_id", "TanggalAjukan",
                "Status", "TanggalSelesai", "Petugas_id"};
        JComponent[] isian = {txtMahasiswa_id, txtJenisSurat_id,
                txtTanggalAjukan, txtStatus, txtTanggalSelesai, txtPetugas_id};

        String[] kolom = {"Mahasiswa_id", "JenisSurat_id", "TanggalAjukan",
                "Status", "TanggalSelesai", "Petugas_id"};

        model = new GenericTableModel<Pengajuan>(kolom,
                p -> new Object[]{p.getMahasiswa_id(), p.getJenisSurat_id(),
                        p.getTanggalAjukan(), p.getStatus(),
                        p.getTanggalSelesai(), p.getPetugas_id()});
        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() >= 0) {
                pilihBaris(model.baris(tabel.getSelectedRow()));
            }
        });

        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnBaru.addActionListener(e -> kosongkan());
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(panelIsian("Isian Pengajuan", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Pengajuan> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtMahasiswa_id.setText("");
        txtJenisSurat_id.setText("");
        txtTanggalAjukan.setText("");
        txtStatus.setText("");
        txtTanggalSelesai.setText("");
        txtPetugas_id.setText("");
        tabel.clearSelection();
        txtMahasiswa_id.requestFocus();
    }

    private void pilihBaris(Pengajuan p) {
        idTerpilih = p.getId();
        txtMahasiswa_id.setText(String.valueOf(p.getMahasiswa_id()));
        txtJenisSurat_id.setText(String.valueOf(p.getJenisSurat_id()));
        txtTanggalAjukan.setText(p.getTanggalAjukan());
        txtStatus.setText(p.getStatus());
        txtTanggalSelesai.setText(p.getTanggalSelesai());
        txtPetugas_id.setText(String.valueOf(p.getPetugas_id()));
    }

    /** Mengubah isian angka menjadi int; isian bukan angka menjadi ValidationException. */
    private int angka(JTextField f, String namaField) throws ValidationException {
        try {
            return Integer.parseInt(f.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(namaField, "harus berupa angka");
        }
    }

    /** Mengambil isian layar menjadi objek. Belum divalidasi di sini. */
    private Pengajuan dariIsian() throws ValidationException {
        Pengajuan p = new Pengajuan(
                angka(txtMahasiswa_id, "Mahasiswa_id"),
                angka(txtJenisSurat_id, "JenisSurat_id"),
                txtTanggalAjukan.getText().trim(),
                txtStatus.getText(),
                txtTanggalSelesai.getText().trim(),
                angka(txtPetugas_id, "Petugas_id"));
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Pengajuan p = dariIsian();
            p.setId(0);
            dao.simpan(p);
            pesan("Tersimpan dengan id " + p.getId() + ".");
            kosongkan();
            muatTabel();
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void ubah() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        try {
            if (dao.ubah(dariIsian())) {
                pesan("Perubahan disimpan.");
                muatTabel();
            } else {
                pesan("Tidak ada baris yang berubah.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void hapus() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        if (!konfirmasi("Hapus pengajuan dengan id " + idTerpilih + "?")) {
            return;
        }
        try {
            if (dao.hapus(idTerpilih)) {
                pesan("Satu baris dihapus.");
                kosongkan();
                muatTabel();
            } else {
                pesan("Tidak ada baris yang terhapus.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormPengajuan().setVisible(true);
            }
        });
    }
}