CREATE TABLE IF NOT EXISTS `questionnaire` (
  `qu_id` int NOT NULL AUTO_INCREMENT,
  `qu_name` varchar(45) DEFAULT NULL,
  `qu_content` varchar(45) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  PRIMARY KEY (`qu_id`)
);

CREATE TABLE IF NOT EXISTS `question_info` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `questionnaire_id` int NOT NULL,
  `question` varchar(60) DEFAULT NULL,
  `options` varchar(100) DEFAULT NULL,
  `options_type` varchar(10) NOT NULL,
  `must` tinyint NOT NULL,
  `op_data` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`question_id`)
);

CREATE TABLE IF NOT EXISTS `respondent` (
  `respondent_id` int NOT NULL AUTO_INCREMENT,
  `questionnaire_id` int DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(45) NOT NULL,
  `answer` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`respondent_id`)
);