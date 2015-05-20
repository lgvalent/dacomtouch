-- phpMyAdmin SQL Dump
-- version 2.8.2.4
-- http://www.phpmyadmin.net
-- 
-- Host: localhost:3306
-- Generation Time: May 12, 2015 at 06:43 PM
-- Server version: 5.0.95
-- PHP Version: 5.2.6
-- 
-- Database: `dacom`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `ponto`
-- 

DROP TABLE IF EXISTS `ponto`;
CREATE TABLE IF NOT EXISTS `ponto` (
  `id` int(11) NOT NULL auto_increment,
  `nome` varchar(50) collate latin1_bin default NULL,
  `ultimoRegistro` timestamp NULL default CURRENT_TIMESTAMP,
  `disponivel` tinyint(1) default '0',
  `password` varchar(50) collate latin1_bin default NULL,
  `sala` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1 COLLATE=latin1_bin AUTO_INCREMENT=33 ;

-- 
-- Dumping data for table `ponto`
-- 

INSERT INTO `ponto` (`id`, `nome`, `ultimoRegistro`, `disponivel`, `password`, `sala`) VALUES (26, 'Silvano', '2015-05-12 00:27:47', 0, 'd8578edf8458ce06fbc5bb76a58c5ca4', 2),
(25, 'Hubner', '2015-05-12 15:42:36', 1, '414f229c999477d107d6f94e66624afa', 1),
(24, 'Re', '2015-05-12 08:25:17', 1, 'f4b434a7cce3530291662a030f8d0b06', 1),
(22, 'Kawamoto', '2015-05-11 20:04:40', 1, '195bfff71f9b60bcd0a409d30519abdc', 1),
(23, 'Radames', '2015-05-11 20:27:16', 1, 'b3e92ea524e29e2cdc587c318b55a76a', 2),
(21, 'Steinmacher', '2015-05-11 19:45:06', 0, 'dc06fb67f1567fdd8cb1bb634404580e', 2),
(19, 'Lucio', '2015-05-12 09:14:11', 0, '7eeaecd5eaadc87bb0f3a3162a3ccb25', 2),
(20, 'Liberato', '2015-05-11 19:36:24', 0, 'e973a9ba96f881876783fb373b6bc7cf', 2),
(27, 'Narci', '2015-05-12 10:02:51', 1, '420735b5c240797f54782360ffb2f9a0', 2),
(28, 'Juliano', '2015-05-11 23:51:12', 1, '30035607ee5bb378c71ab434a6d05410', 2),
(29, 'Ivanilton', '2015-05-12 16:40:17', 0, 'a1f4bbc42d76628d34c32c88ac72ddfe', 2),
(30, 'Ana', '2015-05-12 14:22:03', 1, 'ad92fb5c9f9ffd75ad18aed3fc1c5a01', 2),
(31, 'Diego', '2015-05-12 16:40:32', 1, '53d6eab84a07de9915c7bfe2ec98fdd1', 2),
(32, 'Wiese', '2015-05-12 16:57:34', 1, '3e35555bb65f8721e28bafe219ac61f8', 2);

-- --------------------------------------------------------

-- 
-- Table structure for table `sala`
-- 

DROP TABLE IF EXISTS `sala`;
CREATE TABLE IF NOT EXISTS `sala` (
  `id` int(11) NOT NULL auto_increment,
  `nome` varchar(20) character set latin1 collate latin1_bin default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `sala`
-- 

INSERT INTO `sala` (`id`, `nome`) VALUES (1, 'Sala DACOM-COCIC'),
(2, 'Sala de Professores');
