package gui;

import dao.MahasiswaDao;
import exception.AppException;
import model.Mahasiswa;

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

public class FormMahasiswa extends BaseForm {
    private final MahasiswaDao dao = new MahasiswaDao();

    //jangan beri nilai awal disini
    private JTextField txtNim;
    private JTextField txtNama;
    private JTextField txtKelas;

    private GenericTableModel<Mahasiswa> model;
    private JTable tabel;
    private int idTerpilih;

    public FormMahasiswa() {
        super("Pengajuan Surat Keterangan -  Data Mahasiswa", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtNim = new JTextField();
        txtNama = new JTextField();
        txtKelas = new JTextField();

        String[] label = {"NIM", "Nama", "Kelas"};
        JComponent[] isian = {txtNim, txtNama, txtKelas};

        String[] kolom = {"NIM", "Nama", "Kelas"};
        model = new GenericTableModel<Mahasiswa>(kolom,
                p -> new Object[] {p.getNim(), p.getNama(), p.getKelas()});
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
        JButton btnHapus= new JButton("Hapus");

        btnBaru.addActionListener(e-> kosongkan());
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(panelIsian("Isian Mahasiswa", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8,8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Mahasiswa> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtNim.setText("");
        txtNama.setText("");
        txtKelas.setText("");
        tabel.clearSelection();
        txtNim.requestFocus();
    }
    private void pilihBaris(Mahasiswa p) {
        idTerpilih = p.getId();
        txtNim.setText(p.getNim());
        txtNama.setText(p.getNama());
        txtKelas.setText(p.getKelas());
    }

    // mengambil isian layar menjadi objek. belum divalidasi
    private Mahasiswa dariIsian() {
        Mahasiswa p = new Mahasiswa(txtNim.getText(), txtNama.getText(), txtKelas.getText());
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Mahasiswa p = dariIsian();
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
                new FormMahasiswa().setVisible(true);
            }
        });
    }
}
