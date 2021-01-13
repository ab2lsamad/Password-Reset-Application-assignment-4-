-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2021 at 04:51 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `applicationsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_uname` varchar(50) NOT NULL,
  `admin_pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_uname`, `admin_pass`) VALUES
('admin', 'adminpass1');

-- --------------------------------------------------------

--
-- Table structure for table `applicants`
--

CREATE TABLE `applicants` (
  `app_uname` varchar(50) NOT NULL,
  `app_pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `applicants`
--

INSERT INTO `applicants` (`app_uname`, `app_pass`) VALUES
('user1', 'pass1'),
('user2', 'pass2'),
('user3', 'pass3'),
('user4', 'pass4');

-- --------------------------------------------------------

--
-- Table structure for table `applications`
--

CREATE TABLE `applications` (
  `id` int(11) NOT NULL,
  `app_uname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rollno` varchar(20) NOT NULL,
  `program` varchar(10) NOT NULL,
  `dept` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `body` text NOT NULL,
  `date` varchar(10) NOT NULL,
  `app_status` varchar(12) NOT NULL DEFAULT 'Pending',
  `co_approval` varchar(12) NOT NULL DEFAULT 'Pending',
  `co_comment` text NOT NULL,
  `ad_approval` varchar(12) NOT NULL DEFAULT 'Pending',
  `ad_comment` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `applications`
--

INSERT INTO `applications` (`id`, `app_uname`, `name`, `rollno`, `program`, `dept`, `email`, `subject`, `body`, `date`, `app_status`, `co_approval`, `co_comment`, `ad_approval`, `ad_comment`) VALUES
(1, 'user1', 'ali', '1234', 'BS', 'CS', 'ali@gmail.com', 'updated subject', 'updated body', '1/1/2021', '', '', '', '', ''),
(3, 'user2', 'raza', '1234', 'BS', 'CS', 'raza@gmail.com', 'this is subject', 'this is body', '1/1/2021', '', '', '', '', ''),
(4, 'user3', 'rashid', '1245', 'BS', 'CS', 'rashid@gmail.com', 'this is subject', 'this is body', '1/1/2021', '', '', '', '', ''),
(5, 'user3', 'rashid', '1245', 'BS', 'CS', 'rashid@gmail.com', 'another subject', 'another body', '1/1/2021', '', '', '', '', ''),
(6, 'user3', 'ahmed', '2441', 'BS', 'CS', 'ahmed@gmail.com', 'subject', 'body', '3/2/2021', 'Pending', 'Pending', '', 'Pending', ''),
(7, 'user4', 'sameer', '3454', 'BS', 'CS', 'sameer@gmail.com', 'another subject', 'another body', '10/1/2021', 'Pending', 'Pending', '', 'Pending', ''),
(11, 'user1', 'haid', '17271519-001', 'BS', 'CS', '17271519-001@uog.edu.pk', 'Reset Email Password', 'body', '13/1/2021', 'Approve', 'Approve', 'approved it', 'Approve', 'approved now'),
(12, 'user1', 'sameer', '3454', 'BS', 'CS', 'sameer@gmail.com', 'another subject', 'another body', '10/1/2021', 'Disapprove', 'Disapprove', 'approved', 'Pending', '');

-- --------------------------------------------------------

--
-- Table structure for table `coordinator`
--

CREATE TABLE `coordinator` (
  `co_uname` varchar(50) NOT NULL,
  `co_pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `coordinator`
--

INSERT INTO `coordinator` (`co_uname`, `co_pass`) VALUES
('coordinator', 'copass1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_uname`);

--
-- Indexes for table `applicants`
--
ALTER TABLE `applicants`
  ADD PRIMARY KEY (`app_uname`);

--
-- Indexes for table `applications`
--
ALTER TABLE `applications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `app_uname` (`app_uname`);

--
-- Indexes for table `coordinator`
--
ALTER TABLE `coordinator`
  ADD PRIMARY KEY (`co_uname`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `applications`
--
ALTER TABLE `applications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `applications`
--
ALTER TABLE `applications`
  ADD CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`app_uname`) REFERENCES `applicants` (`app_uname`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
