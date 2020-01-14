# create databases
CREATE DATABASE IF NOT EXISTS `project_database`;
CREATE DATABASE IF NOT EXISTS `db_micro_employee`;

# create root user and grant rights
CREATE USER 'root'@'localhost' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
