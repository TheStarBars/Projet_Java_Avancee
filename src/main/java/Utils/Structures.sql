-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : mar. 13 mai 2025 à 09:05
-- Version du serveur : 5.7.24
-- Version de PHP : 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;



CREATE TABLE `commandes` (
                             `id` int(11) NOT NULL,
                             `id_table` int(11) DEFAULT NULL,
                             `liste_plats` json DEFAULT NULL,
                             `statut` varchar(255) DEFAULT NULL,
                             `date_heure_service` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `employe` (
                           `id` int(11) NOT NULL,
                           `nom` varchar(255) DEFAULT NULL,
                           `poste` varchar(255) DEFAULT NULL,
                           `nb_heure_travaillee` int(11) DEFAULT NULL,
                           `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `plats` (
                         `id` int(11) NOT NULL,
                         `nom` varchar(255) NOT NULL,
                         `description` text NOT NULL,
                         `prix` double NOT NULL,
                         `cout` double NOT NULL,
                         `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `tables` (
                          `id` int(11) NOT NULL,
                          `numero` int(11) NOT NULL,
                          `taille` int(11) NOT NULL,
                          `statut` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `tresorerie` (
                              `id` int(11) NOT NULL,
                              `gain` int(11) DEFAULT NULL,
                              `depense` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `commandes`
    ADD PRIMARY KEY (`id`),
  ADD KEY `id_table` (`id_table`);


ALTER TABLE `employe`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `plats`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `tables`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `tresorerie`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `plats`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `tables`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `commandes`
    ADD CONSTRAINT `commandes_ibfk_1` FOREIGN KEY (`id_table`) REFERENCES `tables` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;