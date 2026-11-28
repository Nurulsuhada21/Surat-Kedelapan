-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2026 at 12:03 PM
-- Server version: 10.11.16-MariaDB
-- PHP Version: 8.3.36

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
-- Table structure for table `pengajuan`
--

CREATE TABLE `pengajuan` (
  `Pengajuan_id` int(11) NOT NULL,
  `Mahasiswa_id` int(11) NOT NULL,
  `JenisSurat_id` int(11) NOT NULL,
  `TanggalAjukan` date NOT NULL,
  `Status` varchar(50) NOT NULL,
  `TanggalSelesai` date DEFAULT NULL,
  `Petugas_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengajuan`
--

INSERT INTO `pengajuan` (`Pengajuan_id`, `Mahasiswa_id`, `JenisSurat_id`, `TanggalAjukan`, `Status`, `TanggalSelesai`, `Petugas_id`) VALUES
(2, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(3, 1, 1, '2026-12-21', 'diajukan', NULL, 1),
(4, 1, 1, '2026-12-21', 'diajukan', NULL, 2),
(6, 1, 1, '2026-11-23', 'Diproses', NULL, 1),
(7, 1, 1, '2026-11-23', 'Diproses', NULL, 1),
(9, 2, 3, '2026-12-21', 'selesai', '2026-12-25', 2),
(10, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(11, 1, 1, '2026-11-17', 'Diajukan', NULL, 1),
(13, 1, 1, '2026-11-23', 'Diajukan', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD PRIMARY KEY (`Pengajuan_id`),
  ADD KEY `Mahasiswa_id` (`Mahasiswa_id`),
  ADD KEY `JenisSurat_id` (`JenisSurat_id`),
  ADD KEY `Petugas_id` (`Petugas_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pengajuan`
--
ALTER TABLE `pengajuan`
  MODIFY `Pengajuan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD CONSTRAINT `pengajuan_ibfk_1` FOREIGN KEY (`Mahasiswa_id`) REFERENCES `mahasiswa` (`Mahasiswa_id`),
  ADD CONSTRAINT `pengajuan_ibfk_2` FOREIGN KEY (`JenisSurat_id`) REFERENCES `jenis_surat` (`JenisSurat_id`),
  ADD CONSTRAINT `pengajuan_ibfk_3` FOREIGN KEY (`Petugas_id`) REFERENCES `petugas` (`Petugas_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
