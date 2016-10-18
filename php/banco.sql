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
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1 COLLATE=latin1_bin AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `ponto`
-- 

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
