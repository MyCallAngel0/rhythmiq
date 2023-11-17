CREATE DATABASE rhythmiqdb;
USE rhythmiqdb;


CREATE TABLE IF NOT EXISTS `user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(50) NOT NULL,
    `last_name` varchar(50) NOT NULL,
    `email` varchar(50) UNIQUE NOT NULL,
    `account_name` varchar(50) UNIQUE NOT NULL,
    `date_of_birth` date NOT NULL,
    `gender` enum('M','F','O') NOT NULL,
    `password` varchar(255) NOT NULL,
    `role` enum('user','artist', 'admin') DEFAULT 'user',
    PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `albums` (
    `album_id` int NOT NULL AUTO_INCREMENT,
    `album_title` varchar(50) DEFAULT NULL,
    `artist_id` int NOT NULL,
    PRIMARY KEY (`album_id`),
    CONSTRAINT `artist_id` FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `songs`
(
    `song_id` int NOT NULL AUTO_INCREMENT,
    `song_title` varchar(50) NOT NULL,
    `artist_id`  int NOT NULL,
    `album_id` INT DEFAULT NULL,
    PRIMARY KEY (`song_id`),
    FOREIGN KEY (`album_id`) REFERENCES `albums` (`album_id`),
    FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `playlists` (
    `playlist_id` int NOT NULL AUTO_INCREMENT,
    `playlist_name` varchar(50) DEFAULT NULL,
    `artist_id` int NOT NULL,
    PRIMARY KEY (`playlist_id`),
    FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `playlist_songs` (
    `playlist_id` INT,
    `song_id` INT,
    PRIMARY KEY (`playlist_id`, `song_id`),
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists`(`playlist_id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs`(`song_id`)
);