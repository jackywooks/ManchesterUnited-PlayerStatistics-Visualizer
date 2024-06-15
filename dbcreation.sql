CREATE DATABASE stat;
USE stat;
CREATE TABLE playerStat (
	player_id INT PRIMARY KEY,
	player_name VARCHAR(30) NOT NULL,
	started INT,
    goal INT,
    assist INT,
    playing_minutes INT,
    yellow_card INT,
    red_card INT,
    rating DECIMAL(5, 2)
);

SELECT * FROM playerStat;
INSERT INTO playerStat (id, player_name, player_position, goal,assist, rating) VALUES (
"Bruno Fernandez","MF",10,8,7.83);
INSERT INTO playerStat (player_name, player_position, goal,assist,rating) VALUES (
"Rasmus Højlund","FW",10,2,6.83);
