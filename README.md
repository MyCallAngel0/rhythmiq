# rhythmiq
Before executing the above query, execute this query first:
```
CREATE DATABASE rhythmiqdb;
USE rhythmiqdb;
```
To have the same user, execute the following query:
```
CREATE USER 'rhythmiq'@'localhost' IDENTIFIED BY 'sql123';
GRANT ALL PRIVILEGES ON rhythmiqdb.* TO 'rhythmiq'@'localhost';
```
