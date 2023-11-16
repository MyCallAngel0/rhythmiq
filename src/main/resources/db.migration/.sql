DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `user_id` int NOT NULL AUTO_INCREMENT,
                        `first_name` varchar(30) NOT NULL,
                        `last_name` varchar(30) NOT NULL,
                        `email` varchar(30) NOT NULL,
                        `date_of_birth` date DEFAULT NULL,
                        `gender` enum('M','F','O') NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `role` enum('user','artist') DEFAULT 'user',
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `albums` (
                          `album_id` int NOT NULL AUTO_INCREMENT,
                          `album_title` varchar(50) DEFAULT NULL,
                          `artist_id` int NOT NULL,
                          PRIMARY KEY (`album_id`),
                          KEY `artist_id` (`artist_id`),
                          CONSTRAINT `artist_id` FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `songs` (
                         `song_id` int NOT NULL AUTO_INCREMENT,
                         `song_title` varchar(50) DEFAULT NULL,
                         `artist_id` int NOT NULL,
                         PRIMARY KEY (`song_id`),
                         FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `playlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlists` (
                             `playlist_id` int NOT NULL AUTO_INCREMENT,
                             `playlist_name` varchar(50) DEFAULT NULL,
                             `artist_id` int NOT NULL,
                             PRIMARY KEY (`playlist_id`),
                             FOREIGN KEY (`artist_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE playlist_songs (
                                playlist_id INT,
                                song_id INT,
                                PRIMARY KEY (playlist_id, song_id),
                                FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id),
                                FOREIGN KEY (song_id) REFERENCES songs(song_id)
);

CREATE TABLE album_songs (
                             album_id INT,
                             song_id INT,
                             PRIMARY KEY (album_id, song_id),
                             FOREIGN KEY (album_id) REFERENCES albums(album_id),
                             FOREIGN KEY (song_id) REFERENCES songs(song_id)
);

CREATE USER 'rhythmiq'@'localhost' IDENTIFIED BY 'sql123';
GRANT ALL PRIVILEGES ON rhythmiqdb.* TO 'rhythmiq'@'localhost';