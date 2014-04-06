CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

CREATE TABLE `library_db`.`books` (
  `books_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `books_title` varchar(100) NOT NULL,
  `books_author` varchar(100) NOT NULL,
  `books_rating` tinyint(4) DEFAULT NULL,
  `books_comment` text,
  `books_start_date` date DEFAULT NULL,
  `books_end_date` date DEFAULT NULL,
  `books_picture_url` varchar(100) DEFAULT NULL,
  `books_add_date` datetime NOT NULL,
  PRIMARY KEY (`books_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `quotes` (
  `quotes_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `quotes_book_id` int(8) unsigned DEFAULT NULL,
  `quotes_text` mediumtext NOT NULL,
  `quotes_add_datetime` datetime NOT NULL,
  `quotes_source` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`quotes_id`),
  UNIQUE KEY `quotes_id_UNIQUE` (`quotes_id`),
  KEY `books_book_id_idx` (`quotes_book_id`),
	CONSTRAINT `quotes_books` FOREIGN KEY (`quotes_book_id`) 
	REFERENCES `books` (`books_id`) 
	ON DELETE SET NULL 
	ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


