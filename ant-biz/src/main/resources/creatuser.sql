CREATE USER 'farming'@'127.0.0.1' IDENTIFIED BY '123456';
flush privileges;
 create database farming;
 alter database farming character set utf8;
 grant all privileges on farming.* to farming@localhost identified by '123456';
 flush privileges;
 
 ---GRANT USAGE ON *.* TO 'appmonitor'@'localhost' IDENTIFIED BY '123456' WITH GRANT OPTION;