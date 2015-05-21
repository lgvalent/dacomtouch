-- phpMyAdmin SQL Dump
-- version 2.8.2.4
-- http://www.phpmyadmin.net
-- 
-- Host: localhost:3306
-- Generation Time: May 21, 2015 at 07:34 PM
-- Server version: 5.0.95
-- PHP Version: 5.2.6
-- 
-- Database: `dacom`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `ponto`
-- 

CREATE TABLE `ponto` (
  `id` int(11) NOT NULL auto_increment,
  `nome` varchar(50) collate latin1_bin default NULL,
  `ultimoRegistro` timestamp NULL default CURRENT_TIMESTAMP,
  `disponivel` tinyint(1) default '0',
  `password` varchar(50) collate latin1_bin default NULL,
  `sala` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=latin1 COLLATE=latin1_bin AUTO_INCREMENT=45 ;

-- 
-- Dumping data for table `ponto`
-- 

INSERT INTO `ponto` (`id`, `nome`, `ultimoRegistro`, `disponivel`, `password`, `sala`) VALUES 
(1, 'Lucio', '2015-05-21 16:47:15', 1, 'e13748298cfb23c19fdfd134a2221e7b', 2);

-- --------------------------------------------------------

-- 
-- Table structure for table `sala`
-- 

CREATE TABLE `sala` (
  `id` int(11) NOT NULL auto_increment,
  `nome` varchar(20) character set latin1 collate latin1_bin default NULL,
  `ultimaAtualizacao` timestamp NULL default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

-- 
-- Dumping data for table `sala`
-- 

INSERT INTO `sala` (`id`, `nome`, `ultimaAtualizacao`) VALUES (1, 'Sala DACOM-COCIC', '2015-05-20 22:42:08'),
(2, 'Sala de Professores', '2015-05-21 19:34:36');
