-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 09 mars 2025 à 19:36
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `medalert`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

DROP TABLE IF EXISTS `administrateur`;
CREATE TABLE IF NOT EXISTS `administrateur` (
  `adminid` int(11) NOT NULL AUTO_INCREMENT,
  `identifiant` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `mdp` varchar(255) DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `specialite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adminid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`adminid`, `identifiant`, `nom`, `mdp`, `statut`, `specialite`) VALUES
(1, 'ddupont', 'Dupont', '1234', 'Médecin', 'Médecine Générale'),
(2, 'llemoine', 'Lemoine', 'abcd', 'Infirmier', NULL),
(3, 'cmartin', 'Martin', 'qwerty', 'Médecin', 'Cardiologie'),
(4, 'ddurand', 'Durand', 'pass123', 'Médecin', 'Dentisterie'),
(5, 'bbernard', 'Bernard', '123abc', 'Infirmier', NULL),
(6, 'rlemoine', 'Lemoine', 'xyz123', 'Médecin', 'Radiologie'),
(7, 'mmoreau', 'Moreau', 'secret', 'Infirmier', NULL),
(8, 'rrobert', 'Robert', 'admin1', 'Médecin', 'Ophtalmologie'),
(9, 'ppires', 'Pires', 'abcd12', 'Infirmier', NULL),
(10, 'aevans', 'Evans', '111222', 'Médecin', 'Dermatologie'),
(11, 'cferrier', 'Ferrier', 'abc123', 'Infirmier', NULL),
(12, 'lgirard', 'Girard', 'password', 'Médecin', 'Chirurgie'),
(13, 'lmarchand', 'Marchand', 'test123', 'Infirmier', NULL),
(14, 'mmartin', 'Martin', 'xyz456', 'Médecin', 'Pédiatrie');

-- --------------------------------------------------------

--
-- Structure de la table `constantes`
--

DROP TABLE IF EXISTS `constantes`;
CREATE TABLE IF NOT EXISTS `constantes` (
  `idconstantes` int(11) NOT NULL AUTO_INCREMENT,
  `datemesure` datetime(6) DEFAULT NULL,
  `temperature` float DEFAULT NULL,
  `pouls` float DEFAULT NULL,
  `SpO2` float DEFAULT NULL,
  `glycemie` float DEFAULT NULL,
  `imc` float DEFAULT NULL,
  `albumine` float DEFAULT NULL,
  `patientid` int(11) NOT NULL,
  `adminid` int(11) NOT NULL,
  PRIMARY KEY (`idconstantes`),
  KEY `CONSTANTES_PATIENT_FK` (`patientid`),
  KEY `CONSTANTES_ADMINISTRATEUR0_FK` (`adminid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `constantes`
--

INSERT INTO `constantes` (`idconstantes`, `datemesure`, `temperature`, `pouls`, `SpO2`, `glycemie`, `imc`, `albumine`, `patientid`, `adminid`) VALUES
(1, '2025-02-01 00:00:00.000000', 36.8, 72, 98, 1, 23.5, 45, 1, 5),
(2, '2025-02-04 00:00:00.000000', 37.2, 78, 97, 1.1, 23.6, 44, 1, 5),
(3, '2025-02-07 00:00:00.000000', 37.5, 80, 97, 1.2, 23.7, 43, 1, 5),
(4, '2025-02-10 00:00:00.000000', 38, 84, 96, 1.3, 23.8, 42, 1, 5),
(5, '2025-02-13 00:00:00.000000', 38.5, 88, 95, 1.4, 23.9, 41, 1, 5),
(6, '2025-02-01 00:00:00.000000', 37, 82, 96, 1.2, 25, 42, 2, 9),
(7, '2025-02-04 00:00:00.000000', 37.1, 90, 95, 1.3, 25.2, 41, 2, 9),
(8, '2025-02-07 00:00:00.000000', 37.1, 110, 94, 1.3, 25.5, 40, 2, 9),
(9, '2025-02-10 00:00:00.000000', 36.9, 100, 93, 1.4, 25.8, 39, 2, 9),
(10, '2025-02-13 00:00:00.000000', 36.8, 98, 92, 1.4, 26, 38, 2, 9),
(11, '2025-02-02 00:00:00.000000', 37.2, 70, 97, 1, 22.8, 45, 3, 11),
(12, '2025-02-05 00:00:00.000000', 37.5, 75, 96, 1.1, 23, 44, 3, 11),
(13, '2025-02-08 00:00:00.000000', 38, 80, 95, 1.1, 23.2, 43, 3, 11),
(14, '2025-02-11 00:00:00.000000', 38.3, 85, 94, 1.2, 23.4, 42, 3, 11),
(15, '2025-02-14 00:00:00.000000', 38.7, 90, 94, 1.3, 23.6, 40, 3, 11),
(16, '2025-02-03 00:00:00.000000', 37.1, 100, 99, 1, 18.5, 50, 4, 13),
(17, '2025-02-06 00:00:00.000000', 37.8, 105, 98, 1, 18.6, 49, 4, 13),
(18, '2025-02-09 00:00:00.000000', 38, 110, 97, 1, 18.7, 48, 4, 13),
(19, '2025-02-12 00:00:00.000000', 38.3, 115, 96, 1, 18.8, 47, 4, 13),
(20, '2025-02-15 00:00:00.000000', 38.5, 120, 94, 1, 18.9, 46, 4, 13),
(21, '2025-02-01 00:00:00.000000', 36.6, 74, 97, 1, 29, 46, 5, 7),
(22, '2025-02-04 00:00:00.000000', 36.7, 72, 97, 1, 30, 45, 5, 7),
(23, '2025-02-07 00:00:00.000000', 36.8, 70, 98, 1, 30.8, 44, 5, 7),
(24, '2025-02-10 00:00:00.000000', 36.6, 68, 98, 1, 31.2, 43, 5, 7),
(25, '2025-02-13 00:00:00.000000', 36.5, 66, 99, 1, 31.8, 42, 5, 7),
(26, '2025-02-02 00:00:00.000000', 36.5, 72, 98, 1.2, 23, 47, 6, 2),
(27, '2025-02-05 00:00:00.000000', 36.6, 74, 98, 1.8, 23.2, 46, 6, 2),
(28, '2025-02-08 00:00:00.000000', 36.6, 76, 97, 2.5, 23.3, 45, 6, 2),
(29, '2025-02-11 00:00:00.000000', 36.4, 74, 97, 3, 23.4, 44, 6, 2),
(30, '2025-02-14 00:00:00.000000', 36.4, 70, 97, 3.2, 23.1, 43, 6, 2),
(31, '2025-02-05 00:00:00.000000', 36.8, 70, 97, 1, 28.3, 38, 7, 5),
(32, '2025-02-08 00:00:00.000000', 36.9, 72, 97, 1, 28, 37, 7, 5),
(33, '2025-02-11 00:00:00.000000', 37, 74, 96, 1, 27.8, 36, 7, 5),
(34, '2025-02-14 00:00:00.000000', 37.2, 76, 95, 1, 27.5, 35, 7, 5),
(35, '2025-02-17 00:00:00.000000', 37.5, 78, 94, 1, 27.2, 34, 7, 5),
(36, '2025-02-02 00:00:00.000000', 36.9, 73, 98, 1, 22, 39, 8, 13),
(37, '2025-02-06 00:00:00.000000', 37.2, 78, 97, 1, 22.1, 38, 8, 13),
(38, '2025-02-09 00:00:00.000000', 37.5, 85, 96, 1, 22.2, 37, 8, 13),
(39, '2025-02-12 00:00:00.000000', 38, 88, 95, 1, 22.1, 36, 8, 13),
(40, '2025-02-15 00:00:00.000000', 38.2, 90, 95, 1, 22, 35, 8, 13),
(41, '2025-02-27 14:25:00.000000', 36, 90, 95, 1, 24, 44, 10, 1),
(42, '2025-02-27 14:39:00.000000', 36, 90, 95, 1, 24, 44, 9, 1);

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

DROP TABLE IF EXISTS `patient`;
CREATE TABLE IF NOT EXISTS `patient` (
  `patientid` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `datedenaissance` datetime(6) DEFAULT NULL,
  `adminid` int(11) NOT NULL,
  PRIMARY KEY (`patientid`),
  KEY `PATIENT_ADMINISTRATEUR_FK` (`adminid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`patientid`, `nom`, `prenom`, `service`, `mail`, `datedenaissance`, `adminid`) VALUES
(1, 'Martin', 'Lucas', 'Médecine Générale', 'lucas.martin@example.com', '1985-06-15 00:00:00.000000', 1),
(2, 'Durand', 'Emma', 'Cardiologie', 'emma.durand@example.com', '1992-04-22 00:00:00.000000', 3),
(3, 'Lefebvre', 'Thomas', 'Chirurgie', 'thomas.lefebvre@example.com', '1988-09-10 00:00:00.000000', 12),
(4, 'Morel', 'Sophie', 'Pédiatrie', 'sophie.morel@example.com', '1995-11-05 00:00:00.000000', 14),
(5, 'Dubois', 'Nicolas', 'Radiologie', 'nicolas.dubois@example.com', '1980-07-30 00:00:00.000000', 6),
(6, 'Laurent', 'Camille', 'Ophtalmologie', 'camille.laurent@example.com', '1993-03-18 00:00:00.000000', 8),
(7, 'Bernard', 'Hugo', 'Dermatologie', 'hugo.bernard@example.com', '1986-12-25 00:00:00.000000', 10),
(8, 'Robert', 'Julie', 'Dentisterie', 'julie.robert@example.com', '1998-05-14 00:00:00.000000', 4),
(9, 'Petit', 'Antoine', 'Cardiologie', 'antoine.petit@example.com', '1991-08-19 00:00:00.000000', 3),
(10, 'Richard', 'Elodie', 'Médecine Générale', 'elodie.richard@example.com', '1979-02-07 00:00:00.000000', 1),
(11, 'toto', 'sidiki', 'Cardiologie', 'sidiki@toto.com', '2025-02-07 07:39:00.000000', 3);

-- --------------------------------------------------------

--
-- Structure de la table `rapports`
--

DROP TABLE IF EXISTS `rapports`;
CREATE TABLE IF NOT EXISTS `rapports` (
  `idrapport` int(11) NOT NULL AUTO_INCREMENT,
  `daterapport` datetime(6) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  `adminid` int(11) NOT NULL,
  `patientid` int(11) NOT NULL,
  PRIMARY KEY (`idrapport`),
  KEY `RAPPORTS_ADMINISTRATEUR_FK` (`adminid`),
  KEY `RAPPORTS_PATIENT0_FK` (`patientid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `constantes`
--
ALTER TABLE `constantes`
  ADD CONSTRAINT `CONSTANTES_ADMINISTRATEUR0_FK` FOREIGN KEY (`adminid`) REFERENCES `administrateur` (`adminid`),
  ADD CONSTRAINT `CONSTANTES_PATIENT_FK` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`);

--
-- Contraintes pour la table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `PATIENT_ADMINISTRATEUR_FK` FOREIGN KEY (`adminid`) REFERENCES `administrateur` (`adminid`);

--
-- Contraintes pour la table `rapports`
--
ALTER TABLE `rapports`
  ADD CONSTRAINT `RAPPORTS_ADMINISTRATEUR_FK` FOREIGN KEY (`adminid`) REFERENCES `administrateur` (`adminid`),
  ADD CONSTRAINT `RAPPORTS_PATIENT0_FK` FOREIGN KEY (`patientid`) REFERENCES `patient` (`patientid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
