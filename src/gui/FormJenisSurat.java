package gui;

import dao.JenisSuratDao;
import exception.AppException;
import model.Jenis_surat;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormJenisSurat extends BaseForm {

    private final JenisSuratDao dao= new JenisSuratDao();

    // JANGAN diberi nilai awal di sini - lihat kotak peringatan di atas.
    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtSyarat;
    private JTextField txtLamaProsesHari;

    private GenericTableModel<Jenis_surat> model;
    private JTable tabel;
    private int idTerpilih;

    public FormJenisSurat() {
        super("Pengajuan Surat Keterangan - Jenis Surat", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtKode = new JTextField();
        txtNama = new JTextField();
        txtSyarat = new JTextField();
        txtLamaProsesHari = new JTextField();

        String[] label = {"Kode", "Nama", "Syarat", "Lama_proses_hari"};
        JComponent[] isian = {txtKode, txtNama, txtSyarat, txtLamaProsesHari};

        String[] kolom = {"Kode", "Nama", "Syarat", "Lama_proses_hari"};
        model = new GenericTableModel<Jenis_surat>(kolom,
                p -> new Object[]{p.getKode(), p.getNama(),
                        p.getSyarat(), p.getLama_proses_hari()});
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
        atas.add(panelIsian("Isian Jenis Surat", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }
    private void muatTabel() {
        try {
            List<Jenis_surat> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtKode.setText("");
        txtNama.setText("");
        txtSyarat.setText("");
        txtLamaProsesHari.setText("");
        tabel.clearSelection();
        txtKode.requestFocus();
    }

    private void pilihBaris(Jenis_surat p) {
        idTerpilih = p.getId();
        txtKode.setText(p.getKode());
        txtNama.setText(p.getNama());
        txtSyarat.setText(p.getSyarat());
        txtLamaProsesHari.setText(p.getLama_proses_hari());

    }

    /** Mengambil isian layar menjadi objek. Belum divalidasi di sini. */
    private Jenis_surat dariIsian() {
        Jenis_surat p = new Jenis_surat(txtKode.getText(), txtNama.getText(),
                txtSyarat.getText(), txtLamaProsesHari.getText());
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Jenis_surat p = dariIsian();
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
        if (!konfirmasi("Hapus penumpang dengan id " + idTerpilih + "?")) {
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
                new FormJenisSurat().setVisible(true);
            }
        });
    }
}