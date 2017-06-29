create database Quiz_Database;
use Quiz_Database;

CREATE TABLE Persons (
    person_id INT NOT NULL AUTO_INCREMENT,
    person_first_name VARCHAR(50) NOT NULL,
    person_last_name VARCHAR(50) NOT NULL,
    person_gender ENUM('male', 'female') NOT NULL,
    person_birthdate DATETIME NOT NULL,
    person_email VARCHAR(100) NOT NULL,
    CONSTRAINT Persons_pk PRIMARY KEY (person_id),
    CONSTRAINT Persons_uk UNIQUE (person_email)
);

CREATE TABLE Accounts (
    account_id INT NOT NULL AUTO_INCREMENT,
    account_user_name VARCHAR(50) NOT NULL,
    account_password VARCHAR(50) NOT NULL,
    CONSTRAINT Accounts_pk PRIMARY KEY (account_id),
    CONSTRAINT Accounts_uk UNIQUE (account_user_name)
);

CREATE TABLE person_account_map (
    person_id INT NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT map_fk1 FOREIGN KEY (person_id)
        REFERENCES Persons (person_id),
    CONSTRAINT map_fk2 FOREIGN KEY (account_id)
        REFERENCES Accounts (account_id)
);

CREATE TABLE Friends (
    friend_id INT NOT NULL AUTO_INCREMENT,
    account_first INT NOT NULL,
    account_second INT NOT NULL,
    CONSTRAINT Friends_pk PRIMARY KEY (friend_id),
    CONSTRAINT Friends_fk1 FOREIGN KEY (account_first)
        REFERENCES Accounts (account_id),
    CONSTRAINT Freinds_fk2 FOREIGN KEY (account_second)
        REFERENCES Accounts (account_id)
);

CREATE TABLE Messages (
    message_id INT NOT NULL AUTO_INCREMENT,
    sender_id INT NOT NULL,
    reciever_id INT NOT NULL,
    message_text text NOT NULL,
    time_sent DATETIME NOT NULL,
    CONSTRAINT Messages_pk PRIMARY KEY (message_id),
    CONSTRAINT Messages_fk1 FOREIGN KEY (sender_id)
        REFERENCES Accounts (account_id),
    CONSTRAINT Messages_fk2 FOREIGN KEY (reciever_id)
        REFERENCES Accounts (account_id)
);

CREATE TABLE Challenges (
    challenge_id INT NOT NULL AUTO_INCREMENT,
    quiz_challenged VARCHAR(50) NOT NULL,
    sender_id INT NOT NULL,
    reciever_id INT NOT NULL,
    score_challenged INT NOT NULL,
    time_sent DATETIME NOT NULL,
    CONSTRAINT Challenges_pk PRIMARY KEY (challenge_id),
    CONSTRAINT Challenges_fk1 FOREIGN KEY (sender_id)
        REFERENCES Accounts (account_id),
    CONSTRAINT Challenges_fk2 FOREIGN KEY (reciever_id)
        REFERENCES Accounts (account_id)
);

CREATE TABLE Quizes (
    quiz_id INT NOT NULL AUTO_INCREMENT,
    quiz_name VARCHAR(100) NOT NULL,
    CONSTRAINT Quizes_pk PRIMARY KEY (quiz_id),
    CONSTRAINT Quizes_uk UNIQUE (quiz_name)
);

CREATE TABLE Question_type (
    question_type_id INT NOT NULL AUTO_INCREMENT,
    question_type_name VARCHAR(50) NOT NULL,
    CONSTRAINT Question_type_pk PRIMARY KEY (question_type_id),
    CONSTRAINT Question_type_uk UNIQUE (question_type_name)
);

insert into Question_type (queestion_type_name) values
('Question_Response'),
('Fill in the Blank'),
('Multiple Choice'),
('Picture-Respnse Questions'),
('Multi-Answer Questions'),
('Multiple Choice with Multiple Answer');

CREATE TABLE Questions (
    question_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_type_id INT NOT NULL,
    question TEXT NOT NULL,
    CONSTRAINT Questions_pk PRIMARY KEY (question_id),
    CONSTRAINT Question_fk1 FOREIGN KEY (quiz_id)
        REFERENCES Quizes (quiz_id),
    CONSTRAINT Question_fk2 FOREIGN KEY (question_type_id)
        REFERENCES Question_type (question_type_id)
);

CREATE TABLE Answers (
    answer_id INT NOT NULL AUTO_INCREMENT,
    question_id INT NOT NULL,
    answer_type BOOLEAN NOT NULL,
    answer TEXT NOT NULL,
    CONSTRAINT Answers_pk PRIMARY KEY (answer_id),
    CONSTRAINT Answers_fk1 FOREIGN KEY (question_id)
        REFERENCES Questions (question_id)
);

CREATE TABLE Matching_question (
    matching_question_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    matching_question TEXT NOT NULL,
    CONSTRAINT Matching_pk PRIMARY KEY (matching_id),
    CONSTRAINT Matching_question_fk1 FOREIGN KEY (quiz_id)
        REFERENCES Quiz (quiz_id)
);

CREATE TABLE Matching_answer (
    matching_answer_id INT NOT NULL AUTO_INCREMENT,
    matching_question_id INT NOT NULL,
    matching_answer TEXT NOT NULL,
    matching_answers_order TEXT NOT NULL,
    CONSTRAINT Matching_answer_pk PRIMARY KEY (matching_answer_id),
    CONSTRAINT Matching_answer_fk1 FOREIGN KEY (matching_question_id)
        REFERENCES Matching_question (matching_question_id)
);




