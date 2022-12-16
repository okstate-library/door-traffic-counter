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

-- Adding unique to the date column in head_count table. 
ALTER TABLE `headcountdb`.`head_count`;
ALTER TABLE `headcountdb`.`head_count` ALTER INDEX `date_UNIQUE` VISIBLE;

CREATE DEFINER=`root`@`%` PROCEDURE `getStatByDateRange`(

IN start_date  VARCHAR(12), --  = "2022-12-01";
	IN end_date  VARCHAR(12) -- edate = "2022-12-02";
    )
BEGIN
SELECT 
   t2.process_date
    ,
    MAX(CASE
        WHEN t2.process_hour = '0' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr1',
    MAX(CASE
        WHEN t2.process_hour = '1' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr2',
    MAX(CASE
        WHEN t2.process_hour = '2' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr3',
    MAX(CASE
        WHEN t2.process_hour = '3' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr4',
    MAX(CASE
        WHEN t2.process_hour = '4' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr5',
    MAX(CASE
        WHEN t2.process_hour = '5' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr6',
    MAX(CASE
        WHEN t2.process_hour = '6' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr7',
    MAX(CASE
        WHEN t2.process_hour = '7' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr8',
    MAX(CASE
        WHEN t2.process_hour = '8' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr9',
    MAX(CASE
        WHEN t2.process_hour = '9' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr10',
    MAX(CASE
        WHEN t2.process_hour = '10' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr11',
    MAX(CASE
        WHEN t2.process_hour = '11' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr12',
    MAX(CASE
        WHEN t2.process_hour = '12' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr13',
    MAX(CASE
        WHEN t2.process_hour = '13' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr14',
    MAX(CASE
        WHEN t2.process_hour = '14' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr15',
    MAX(CASE
        WHEN t2.process_hour = '15' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr16',
    MAX(CASE
        WHEN t2.process_hour = '16' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr17',
    MAX(CASE
        WHEN t2.process_hour = '17' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr18',
    MAX(CASE
        WHEN t2.process_hour = '18' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr19',
    MAX(CASE
        WHEN t2.process_hour = '19' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr20',
    MAX(CASE
        WHEN t2.process_hour = '20' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr21',
    MAX(CASE
        WHEN t2.process_hour = '21' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr22',
    MAX(CASE
        WHEN t2.process_hour = '22' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr23',
    MAX(CASE
        WHEN t2.process_hour = '23' THEN CONCAT( if(t2.in is null, 0, t2.in), '/', if(t2.out is null, 0, t2.out))
    END) AS 'hr24'
FROM (
    SELECT 
    t.process_date,t.process_hour, MAX(CASE
        WHEN t.process_type = '1' THEN process_count
    END) AS 'in',
    MAX(CASE
        WHEN t.process_type = '0' THEN process_count
    END) AS 'out'
    
    FROM 
    (SELECT 
        DATE(date) AS process_date, 
            HOUR(date) AS process_hour,
            entry AS process_type,
            SUM(count) AS process_count
    FROM
        head_count_stat
    WHERE
        DATE(date) >= start_date
            AND DATE(date) <= end_date
    GROUP BY DATE(date) , HOUR(date) , entry) t 
    GROUP BY t.process_date,t.process_hour)t2
    GROUP BY t2.process_date;
END

CREATE DEFINER=`root`@`%` PROCEDURE `getStatByDate`(
IN sel_date  VARCHAR(12) --  = "2022-12-01";
)
BEGIN


DROP TABLE IF EXISTS temp1;
DROP TABLE IF EXISTS temp2;

CREATE TEMPORARY TABLE temp1 ENGINE=MEMORY  (select id, HOUR(date) as date_hour,live_count,entry,count from head_count_stat WHERE
            DATE(date) = sel_date);
            
CREATE TEMPORARY TABLE temp2 ENGINE=MEMORY  (
SELECT date_hour,SUBSTRING_INDEX(
      MIN(CONCAT(id, '|', IFNULL(live_count, ''))
    ), '|', -1) as live_count
FROM temp1
GROUP BY date_hour);      
                 
SELECT 
    t.process_hour,
    (SELECT 
            live_count
        FROM
            temp2
        WHERE
            date_hour = t.process_hour
        ) AS live_count,
    MAX(CASE
        WHEN t.process_type = '1' THEN process_count
    END) AS 'in',
    MAX(CASE
        WHEN t.process_type = '0' THEN process_count
    END) AS 'out'
FROM
    (SELECT 
        date_hour AS process_hour,
            entry AS process_type,
            SUM(count) AS process_count
    FROM
        temp1
    GROUP BY date_hour , entry) t
GROUP BY t.process_hour;
END
