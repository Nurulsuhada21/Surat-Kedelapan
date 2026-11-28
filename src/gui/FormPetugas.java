package gui;

import dao.PetugasDao;
import exception.AppException;
import model.Petugas;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.List;

public class FormPetugas extends BaseForm {

    private final PetugasDao dao = new PetugasDao();

    private JTextField txtNama;
    private JTextField txtJenisKelamin;
    private JTextField txtTelepon;
    private JTextField txtAlamat;

    private GenericTableModel<Petugas> model;
    private JTable tabel;
    private int idTerpilih;

    public FormPetugas() {
        super("Surat Kedelapan - Data Petugas", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtNama = new JTextField();
        txtJenisKelamin = new JTextField();
        txtTelepon = new JTextField();
        txtAlamat = new JTextField();

        String[] label = {"Nama", "Jenis Kelamin", "Telepon", "Alamat"};
        JComponent[] isian = {txtNama, txtJenisKelamin, txtTelepon, txtAlamat};

        String[] kolom = {"Nama", "Jenis Kelamin", "Telepon", "Alamat"};
        model = new GenericTableModel<Petugas>(kolom,
                p -> new Object[]{p.getNama(), p.getJenisKelamin(),
                        p.getTelepon(), p.getAlamat()});

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
        atas.add(panelIsian("Isian Petugas", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Petugas> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtNama.setText("");
        txtJenisKelamin.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        tabel.clearSelection();
        txtNama.requestFocus();
    }

    private void pilihBaris(Petugas p) {
        idTerpilih = p.getId();
        txtNama.setText(p.getNama());
        txtJenisKelamin.setText(p.getJenisKelamin());
        txtTelepon.setText(p.getTelepon());
        txtAlamat.setText(p.getAlamat());
    }

    private Petugas dariIsian() {
        Petugas p = new Petugas(
                txtNama.getText(),
                txtJenisKelamin.getText(),
                txtTelepon.getText(),
                txtAlamat.getText()
        );
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Petugas p = dariIsian();
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
        if (!konfirmasi("Hapus petugas dengan id " + idTerpilih + "?")) {
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
                new FormPetugas().setVisible(true);
            }
        });
    }
}
