TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `ic` AFTER INSERT ON `message` FOR EACH ROW BEGIN\n	INSERT INTO count VALUES(new.msgid, 0, 0);\n\n	IF new.theid > 0 THEN\n		UPDATE theme SET count = count+1 WHERE theid = new.theid;\n	END IF;\nEND'
sql_modes=1075838976
definers='root@localhost'
client_cs_names='utf8'
connection_cl_names='utf8_general_ci'
db_cl_names='utf8_general_ci'
