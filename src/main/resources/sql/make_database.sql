CREATE TABLE `head_count` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `count` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `head_count_stat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `is_reset` tinyint(1) NOT NULL DEFAULT '0',
  `entry` tinyint(1) NOT NULL DEFAULT '0',
  `live_count` int NOT NULL,
  `count` int NOT NULL DEFAULT '0',
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
  `role_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



INSERT INTO `role`
(`role_id`,`name`)
VALUES
(0,'ROLE_USER');

INSERT INTO `role`
(`role_id`,`name`)
VALUES
(1,'ROLE_ADMIN');


INSERT INTO `user`
(`user_id`,`first_name`,`last_name`,`email`,`username`,`password`,`enabled`,`deleted`,`role_id`)
VALUES
('1','Lennon','Steven','lstevens@okstate.edu','admin','$2a$12$0nyne1/4.1.28ILaR9CqBuf0Uj.zne2Xr.OkQDd3XPW0OKbkKqrX6',1,0,1);

