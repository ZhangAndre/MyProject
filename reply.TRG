TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `ttt` AFTER INSERT ON `reply` FOR EACH ROW BEGIN\n              update count set replyCount=replyCount+1 where msgid=new.msgid;\nEND'
sql_modes=1075838976
definers='root@localhost'
client_cs_names='utf8'
connection_cl_names='utf8_general_ci'
db_cl_names='utf8_general_ci'
