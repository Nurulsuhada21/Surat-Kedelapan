CREATE DATABASE IF NOT EXISTS `pengajuansurat`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE `pengajuansurat`;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Tabel mahasiswa
CREATE TABLE IF NOT EXISTS `mahasiswa` (
  `mahasiswa_id` int(11) NOT NULL AUTO_INCREMENT,
  `nim` char(10) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(20) NOT NULL,
  PRIMARY KEY (`mahasiswa_id`),
  UNIQUE KEY `nim` (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `mahasiswa` (`mahasiswa_id`, `nim`, `nama`, `kelas`) VALUES
(1, '6304250001', 'Maya', 'RPL 3C'),
(2, '6304250018', 'Nurul Suhada', 'RPL 3C'),
(3, '6304250060', 'Mayanda Febri Yanti', 'RPL 3C'),
(4, '6304250069', 'Nabila Meilia Islahul Jannah', 'RPL 3C'),
(12, '6304250015', 'Mulhim', 'RPL 3C')
ON DUPLICATE KEY UPDATE
  `nim` = VALUES(`nim`),
  `nama` = VALUES(`nama`),
  `kelas` = VALUES(`kelas`);

-- 2. Tabel jenis_surat
CREATE TABLE IF NOT EXISTS `jenis_surat` (
  `jenisSurat_id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` char(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `syarat` varchar(30) NOT NULL,
  `lama_proses_hari` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`jenisSurat_id`),
  UNIQUE KEY `kode` (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `jenis_surat` (`jenisSurat_id`, `kode`, `nama`, `syarat`, `lama_proses_hari`) VALUES
(1, 'SR001', 'Surat Keterangan', '', 0),
(2, 'SR002', 'Surat Keterangan beasiswa', 'KTM, KRS', 2),
(3, 'SR003', 'Surat Keterangan Magang', 'KTM, Tujuan Magang', 4),
(9, 'SR004', 'Surat Rekomendasi', 'KTM', 2),
(16, 'SR008', 'Surat Rekomendasi', 'KTM', 2),
(19, 'SR005', 'Surat Keterangan Sakit', 'KTM, Surat Dokter', 1)
ON DUPLICATE KEY UPDATE
  `kode` = VALUES(`kode`),
  `nama` = VALUES(`nama`),
  `syarat` = VALUES(`syarat`),
  `lama_proses_hari` = VALUES(`lama_proses_hari`);

-- 3. Tabel petugas
CREATE TABLE IF NOT EXISTS `petugas` (
  `petugas_id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `alamat` text DEFAULT NULL,
  PRIMARY KEY (`petugas_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `petugas` (`petugas_id`, `nama`, `jenis_kelamin`, `telepon`, `alamat`) VALUES
(1, 'Petugas Admin 1', 'Perempuan', '081200000001', 'Bengkalis'),
(2, 'Petugas Admin 2', 'Laki-laki', '081200000002', 'Bengkalis')
ON DUPLICATE KEY UPDATE
  `nama` = VALUES(`nama`),
  `jenis_kelamin` = VALUES(`jenis_kelamin`),
  `telepon` = VALUES(`telepon`),
  `alamat` = VALUES(`alamat`);

-- 4. Tabel pengajuan (relasi ke mahasiswa, jenis_surat, petugas)
CREATE TABLE IF NOT EXISTS `pengajuan` (
  `pengajuan_id` int(11) NOT NULL AUTO_INCREMENT,
  `mahasiswa_id` int(11) NOT NULL,
  `jenisSurat_id` int(11) NOT NULL,
  `TanggalAjukan` date NOT NULL,
  `Status` varchar(50) NOT NULL,
  `TanggalSelesai` date DEFAULT NULL,
  `petugas_id` int(11) NOT NULL,
  PRIMARY KEY (`pengajuan_id`),
  KEY `mahasiswa_id` (`mahasiswa_id`),
  KEY `jenisSurat_id` (`jenisSurat_id`),
  KEY `petugas_id` (`petugas_id`),
  CONSTRAINT `pengajuan_ibfk_1` FOREIGN KEY (`mahasiswa_id`) REFERENCES `mahasiswa` (`mahasiswa_id`),
  CONSTRAINT `pengajuan_ibfk_2` FOREIGN KEY (`jenisSurat_id`) REFERENCES `jenis_surat` (`jenisSurat_id`),
  CONSTRAINT `pengajuan_ibfk_3` FOREIGN KEY (`petugas_id`) REFERENCES `petugas` (`petugas_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `pengajuan` (`pengajuan_id`, `mahasiswa_id`, `jenisSurat_id`, `TanggalAjukan`, `Status`, `TanggalSelesai`, `petugas_id`) VALUES
(2, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(3, 1, 1, '2026-12-21', 'diajukan', NULL, 1),
(4, 1, 1, '2026-12-21', 'diajukan', NULL, 2),
(6, 1, 1, '2026-11-23', 'Diproses', NULL, 1),
(7, 1, 1, '2026-11-23', 'Diproses', NULL, 1),
(9, 2, 3, '2026-12-21', 'selesai', '2026-12-25', 2),
(10, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(11, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(13, 1, 1, '2026-11-23', 'Diajukan', NULL, 1)
ON DUPLICATE KEY UPDATE
  `mahasiswa_id` = VALUES(`mahasiswa_id`),
  `jenisSurat_id` = VALUES(`jenisSurat_id`),
  `TanggalAjukan` = VALUES(`TanggalAjukan`),
  `Status` = VALUES(`Status`),
  `TanggalSelesai` = VALUES(`TanggalSelesai`),
  `petugas_id` = VALUES(`petugas_id`);

SET FOREIGN_KEY_CHECKS = 1;


