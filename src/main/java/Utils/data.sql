-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : mar. 13 mai 2025 à 09:26
-- Version du serveur : 5.7.24
-- Version de PHP : 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP TABLE `plats`;

CREATE TABLE `plats` (
                         `id` int(11) NOT NULL,
                         `nom` varchar(255) NOT NULL,
                         `description` text NOT NULL,
                         `prix` double NOT NULL,
                         `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `plats` (`id`, `nom`, `description`, `prix`, `image`) VALUES
    (1, 'Boeuf Bourguignon', 'Le bœuf bourguignon est un plat de viande de bœuf braisée au vin rouge, et servi avec une garniture composée d\'oignons grelots, de champignons et de lardons.', 24.5, ''),
(2, 'Hachis parmentier', 'Le hachis Parmentier, ou hachis parmentier, est un plat parisien à base de purée de pommes de terre et de viande de bœuf hachée', 20.95, ''),
(3, 'Tarte flambée', 'La tarte flambée, ou flammekueche en alsacien, est une recette traditionnelle de la cuisine alsacienne. Elle est composée d\'une fine abaisse[2] de pâte à pain[3] recouverte de crème fraîche épaisse ou de fromage blanc, d\'oignons et de lardons,', 19.5, '');


ALTER TABLE `plats`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `plats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;