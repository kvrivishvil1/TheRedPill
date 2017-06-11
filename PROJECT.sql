create database Quiz_Database;
use Quiz_Database;

CREATE TABLE Persons (
    person_id INT NOT NULL AUTO_INCREMENT,
    person_firstName VARCHAR(50) NOT NULL,
    person_lastName VARCHAR(50) NOT NULL,
    person_gender ENUM('male', 'female') NOT NULL,
    person_birthdate DATETIME,
    
    
    CONSTRAINT Persons_pk PRIMARY KEY (person_id)
);

CREATE TABLE Accounts (
    account_id INT NOT NULL AUTO_INCREMENT,
    account_userName VARCHAR(50) NOT NULL,
    account_password VARCHAR(50) NOT NULL,
    
    
    CONSTRAINT Accounts_pk PRIMARY KEY (account_id)
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
    message_text VARCHAR(100),
    time_sent DATETIME,
    
    
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
    time_sent DATETIME,
    
    
    CONSTRAINT Challenges_pk PRIMARY KEY (challenge_id),
    CONSTRAINT Challenges_fk1 FOREIGN KEY (sender_id)
        REFERENCES Accounts (account_id),
    CONSTRAINT Challenges_fk2 FOREIGN KEY (reciever_id)
        REFERENCES Accounts (account_id)
);




