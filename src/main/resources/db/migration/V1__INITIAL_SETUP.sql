CREATE TABLE player (
  id INT NOT NULL PRIMARY KEY,
  username VARCHAR(999) NOT NULL,
  passwordhash VARCHAR(999) NOT NULL,
  role VARCHAR(999) NOT NULL
);
CREATE TABLE question (
  id INT NOT NULL PRIMARY KEY,
  question VARCHAR(999) NOT NULL,
  answer VARCHAR(999) NOT NULL,
  firsthint VARCHAR(999) NOT NULL,
  secondhint VARCHAR(999) NOT NULL,
  submittedbyuserid INT
);
CREATE TABLE game (
  id INT NOT NULL PRIMARY KEY,
  creationtimestamp TIMESTAMP NOT NULL,
  lastmodifiedtimestamp TIMESTAMP NOT NULL,
  createdbyuserid INT NOT NULL,
  name VARCHAR(999)
);
CREATE SEQUENCE seq_player START 1;
CREATE SEQUENCE seq_game START 1;
CREATE SEQUENCE seq_question START 1;
CREATE TABLE game2player (
  gameid INT NOT NULL,
  playerid INT NOT NULL
);
CREATE TABLE game2question (
  gameid INT NOT NULL,
  questionid INT NOT NULL
);