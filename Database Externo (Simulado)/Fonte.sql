-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 06-Nov-2016 às 17:44
-- Versão do servidor: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `escolaapp`
--
CREATE DATABASE IF NOT EXISTS `escolaapp` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `escolaapp`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `aluno`
--

CREATE TABLE `aluno` (
  `_id` int(11) NOT NULL,
  `nome` text,
  `cpf` text,
  `_idturmaAtual` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `aluno`
--

INSERT INTO `aluno` (`_id`, `nome`, `cpf`, `_idturmaAtual`) VALUES
(1, 'NOME1', 'CPF1', 1),
(2, 'NOME2', 'CPF2', 1),
(3, 'NOME3', 'CPF3', 2),
(4, 'NOME4', 'CPF4', 2),
(5, 'NOME5', 'CPF5', 2),
(6, 'NOME6', 'CPF6', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `presenca`
--

CREATE TABLE `presenca` (
  `_id` int(11) NOT NULL,
  `_idAluno` int(11) NOT NULL,
  `dataa` text,
  `_idTurma` int(11) NOT NULL,
  `presenca` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `professor`
--

CREATE TABLE `professor` (
  `_id` int(11) NOT NULL,
  `nome` text NOT NULL,
  `cpf` text NOT NULL,
  `senha` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `professor`
--

INSERT INTO `professor` (`_id`, `nome`, `cpf`, `senha`) VALUES
(1, 'PROFESSOR1', '1', '1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `professorturma`
--

CREATE TABLE `professorturma` (
  `_idProfessor` int(11) NOT NULL,
  `_idTurma` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `turma`
--

CREATE TABLE `turma` (
  `_id` int(11) NOT NULL,
  `nome` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`_id`),
  ADD KEY `_idturmaAtual` (`_idturmaAtual`);

--
-- Indexes for table `presenca`
--
ALTER TABLE `presenca`
  ADD PRIMARY KEY (`_id`),
  ADD KEY `_idAluno` (`_idAluno`),
  ADD KEY `_idTurma` (`_idTurma`);

--
-- Indexes for table `professor`
--
ALTER TABLE `professor`
  ADD PRIMARY KEY (`_id`);

--
-- Indexes for table `professorturma`
--
ALTER TABLE `professorturma`
  ADD KEY `_idProfessor` (`_idProfessor`),
  ADD KEY `_idTurma` (`_idTurma`);

--
-- Indexes for table `turma`
--
ALTER TABLE `turma`
  ADD PRIMARY KEY (`_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `aluno`
--
ALTER TABLE `aluno`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `presenca`
--
ALTER TABLE `presenca`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `professor`
--
ALTER TABLE `professor`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `turma`
--
ALTER TABLE `turma`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
