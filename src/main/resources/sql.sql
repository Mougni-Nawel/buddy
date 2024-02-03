-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 03 fév. 2024 à 11:01
-- Version du serveur : 8.0.34
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `buddy`
--

-- --------------------------------------------------------

--
-- Structure de la table `amis`
--

DROP TABLE IF EXISTS `amis`;
CREATE TABLE IF NOT EXISTS `amis` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `id_amis` int DEFAULT NULL,
                                      `id_user` int DEFAULT NULL,
                                      `user` int DEFAULT NULL,
                                      PRIMARY KEY (`id`),
    KEY `FKkk67sxb19msp8uykv79d3jdhn` (`user`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `coordonnees_bancaire` varchar(255) DEFAULT NULL,
    `montant` double NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id`, `coordonnees_bancaire`, `montant`) VALUES
                                                                   (1, NULL, 0),
                                                                   (2, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `operation`
--

DROP TABLE IF EXISTS `operation`;
CREATE TABLE IF NOT EXISTS `operation` (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `commission` double NOT NULL,
                                           `date` datetime DEFAULT NULL,
                                           `montant` double NOT NULL,
                                           `numero_transaction` int DEFAULT NULL,
                                           `id_ami` int DEFAULT NULL,
                                           `id_user` int DEFAULT NULL,
                                           PRIMARY KEY (`id`),
    KEY `FKtrgh6vpnsspsje9lkjjtgtitx` (`id_ami`),
    KEY `FKcnur9fjqqhjqd8toied96dg7f` (`id_user`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
                                      `userid` int NOT NULL AUTO_INCREMENT,
                                      `email` varchar(255) DEFAULT NULL,
    `firstname` varchar(255) DEFAULT NULL,
    `lastname` varchar(255) DEFAULT NULL,
    `mdp` varchar(255) DEFAULT NULL,
    `role` smallint DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `compte_id` int DEFAULT NULL,
    PRIMARY KEY (`userid`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
    KEY `FK55wqyp3yo78bxyge0cuswgjeg` (`compte_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`userid`, `email`, `firstname`, `lastname`, `mdp`, `role`, `username`, `compte_id`) VALUES
                                                                                                            (1, 'test@test.com', 'NU0', 'NU0', '$2a$10$qYOepnW6YyoomJiik6N4b.bejPKKk/XnqeUdaGlWmOczYcKyutVM.', 1, NULL, 1),
                                                                                                            (2, 'test1@test.com', 'NU1', 'NU1', '$2a$10$eqth4FXSIYiiKMXHRkQcM.ebG6wVI729RiqDSTlCVtuoffF5h/SvS', 1, NULL, 2);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `amis`
--
ALTER TABLE `amis`
    ADD CONSTRAINT `FKkk67sxb19msp8uykv79d3jdhn` FOREIGN KEY (`user`) REFERENCES `user` (`userid`);

--
-- Contraintes pour la table `operation`
--
ALTER TABLE `operation`
    ADD CONSTRAINT `FKcnur9fjqqhjqd8toied96dg7f` FOREIGN KEY (`id_user`) REFERENCES `user` (`userid`),
  ADD CONSTRAINT `FKtrgh6vpnsspsje9lkjjtgtitx` FOREIGN KEY (`id_ami`) REFERENCES `user` (`userid`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
    ADD CONSTRAINT `FK55wqyp3yo78bxyge0cuswgjeg` FOREIGN KEY (`compte_id`) REFERENCES `compte` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
