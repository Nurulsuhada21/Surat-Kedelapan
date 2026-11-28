-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Nov 2026 pada 13.03
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pengajuansurat`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_surat`
--

CREATE TABLE `jenis_surat` (
  `jenisSurat_id` int(11) NOT NULL,
  `kode` char(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `syarat` varchar(30) NOT NULL,
  `lama_proses_hari` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jenis_surat`
--

INSERT INTO `jenis_surat` (`jenisSurat_id`, `kode`, `nama`, `syarat`, `lama_proses_hari`) VALUES
(1, 'SR001', 'Surat Keterangan', '', 0),
(2, 'SR002', 'Surat Keterangan beasiswa', 'KTM, KRS', 2),
(3, 'SR003', 'Surat Keterangan Magang', 'KTM, Tujuan Magang', 4),
(9, 'SR004', 'Surat Rekomendasi', 'KTM', 2),
(16, 'SR008', 'Surat Rekomendasi', 'KTM', 2),
(19, 'SR005', 'Surat Keterangan Sakit', 'KTM, Surat Dokter', 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `jenis_surat`
--
ALTER TABLE `jenis_surat`
  ADD PRIMARY KEY (`jenisSurat_id`),
  ADD UNIQUE KEY `kode` (`kode`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `jenis_surat`
--
ALTER TABLE `jenis_surat`
  MODIFY `jenisSurat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
