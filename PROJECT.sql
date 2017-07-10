drop database Quiz_Database;
create database Quiz_Database;
use Quiz_Database;


CREATE TABLE persons (
    person_id INT NOT NULL AUTO_INCREMENT,
    person_first_name VARCHAR(50) NOT NULL,
    person_last_name VARCHAR(50) NOT NULL,
    person_gender ENUM('male', 'female') NOT NULL,
    person_birthdate DATETIME NOT NULL,
    person_email VARCHAR(100) NOT NULL,
    CONSTRAINT persons_pk PRIMARY KEY (person_id),
    CONSTRAINT persons_uk UNIQUE (person_email)
);



CREATE TABLE accounts (
    account_id INT NOT NULL AUTO_INCREMENT,
    account_user_name VARCHAR(50) NOT NULL,
    account_password VARCHAR(50) NOT NULL,
    account_status ENUM('user', 'admin') NOT NULL,
    CONSTRAINT accounts_pk PRIMARY KEY (account_id),
    CONSTRAINT accounts_uk UNIQUE (account_user_name)
);



CREATE TABLE person_account_map (
    person_id INT NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT map_fk1 FOREIGN KEY (person_id)
        REFERENCES persons (person_id),
    CONSTRAINT map_fk2 FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
);



CREATE TABLE friends (
    friend_id INT NOT NULL AUTO_INCREMENT,
    account_first INT NOT NULL,
    account_second INT NOT NULL,
    CONSTRAINT friends_pk PRIMARY KEY (friend_id),
    CONSTRAINT friends_fk1 FOREIGN KEY (account_first)
        REFERENCES accounts (account_id),
    CONSTRAINT friends_fk2 FOREIGN KEY (account_second)
        REFERENCES accounts (account_id)
);



CREATE TABLE messages (
    message_id INT NOT NULL AUTO_INCREMENT,
    sender_id INT NOT NULL,
    reciever_id INT NOT NULL,
    message_text TEXT NOT NULL,
    time_sent DATETIME NOT NULL,
    CONSTRAINT messages_pk PRIMARY KEY (message_id),
    CONSTRAINT messages_fk1 FOREIGN KEY (sender_id)
        REFERENCES accounts (account_id),
    CONSTRAINT messages_fk2 FOREIGN KEY (reciever_id)
        REFERENCES accounts (account_id)
);




CREATE TABLE challenges (
    challenge_id INT NOT NULL AUTO_INCREMENT,
    quiz_challenged VARCHAR(50) NOT NULL,
    sender_id INT NOT NULL,
    reciever_id INT NOT NULL,
    score_challenged INT NOT NULL,
    time_sent DATETIME NOT NULL,
    CONSTRAINT challenges_pk PRIMARY KEY (challenge_id),
    CONSTRAINT challenges_fk1 FOREIGN KEY (sender_id)
        REFERENCES accounts (account_id),
    CONSTRAINT challenges_fk2 FOREIGN KEY (reciever_id)
        REFERENCES accounts (account_id)
);


CREATE TABLE quizzes (
    quiz_id INT NOT NULL AUTO_INCREMENT,
    quiz_name VARCHAR(100) NOT NULL,
    is_rearrangable BOOLEAN NOT NULL,
    is_practicable BOOLEAN NOT NULL,
    description TEXT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT quizzes_pk PRIMARY KEY (quiz_id)
);



CREATE TABLE question_types (
    question_type_id INT NOT NULL AUTO_INCREMENT,
    question_type_name VARCHAR(50) NOT NULL,
    CONSTRAINT question_type_pk PRIMARY KEY (question_type_id),
    CONSTRAINT question_type_uk UNIQUE (question_type_name)
);




CREATE TABLE questions (
    question_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_type_id INT NOT NULL,
    question_note TEXT NOT NULL,
    answer_order_sensitivity BOOLEAN NOT NULL,
    CONSTRAINT questions_pk PRIMARY KEY (question_id),
    CONSTRAINT questions_fk1 FOREIGN KEY (quiz_id)
        REFERENCES quizzes (quiz_id),
    CONSTRAINT questions_fk2 FOREIGN KEY (question_type_id)
        REFERENCES question_types (question_type_id)
);



CREATE TABLE subquestions (
    subquestion_id INT NOT NULL AUTO_INCREMENT,
    question_id INT NOT NULL,
    subquestion_text TEXT NOT NULL,
    CONSTRAINT subquestions_pk PRIMARY KEY (subquestion_id),
    CONSTRAINT subquestions_fk FOREIGN KEY (question_id)
        REFERENCES questions (question_id)
);


CREATE TABLE answer_subquestion_map (
    answer_id INT NOT NULL AUTO_INCREMENT,
    subquestion_id INT NOT NULL,
    CONSTRAINT answer_subquestion_map_pk PRIMARY KEY (answer_id),
    CONSTRAINT answer_subquestion_map_fk2 FOREIGN KEY (subquestion_id)
        REFERENCES subquestions (subquestion_id)
);



CREATE TABLE answers (
    answer_id INT NOT NULL,
    answer_text TEXT NOT NULL,
    parser_symbol CHAR NOT NULL,
    CONSTRAINT answers_fk FOREIGN KEY (answer_id)
        REFERENCES answer_subquestion_map (answer_id)
);



CREATE TABLE question_options (
    option_id INT NOT NULL AUTO_INCREMENT,
    question_id INT NOT NULL,
    option_text TEXT NOT NULL,
    CONSTRAINT question_options_pk PRIMARY KEY (option_id),
    CONSTRAINT question_options_fk FOREIGN KEY (question_id)
        REFERENCES questions (question_id)
);



CREATE TABLE quiz_attempts (
    attempt_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    account_id INT NOT NULL,
    score INT NOT NULL,
    start_time DATETIME NOT NULL,
    finish_time DATETIME NOT NULL,
    CONSTRAINT quiz_attempts_pk PRIMARY KEY (attempt_id),
    CONSTRAINT quiz_attempts_fk1 FOREIGN KEY (quiz_id)
        REFERENCES quizzes (quiz_id),
    CONSTRAINT quiz_attempts_fk2 FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
);



CREATE TABLE categories (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    category_image_url VARCHAR(100) NOT NULL,
    CONSTRAINT categories_pk PRIMARY KEY (category_id),
    CONSTRAINT categories_uk1 UNIQUE (category_name)
);


CREATE TABLE quiz_tags (
	tag_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    tag_name VARCHAR(50) NOT NULL,
     CONSTRAINT categories_pk PRIMARY KEY (tag_id),
    CONSTRAINT quiz_tag_fk1 FOREIGN KEY (quiz_id)
        REFERENCES quizzes (quiz_id)
);



CREATE TABLE quiz_reviews (
    review_id INT NOT NULL,
    quiz_id INT NOT NULL,
    account_id INT NOT NULL,
    star INT NOT NULL,
    review_text TEXT NOT NULL,
    review_date DATETIME NOT NULL,
    CONSTRAINT quiz_reviews_pk PRIMARY KEY (review_id),
    CONSTRAINT quiz_reviews_fk1 FOREIGN KEY (quiz_id)
        REFERENCES quizzes (quiz_id),
    CONSTRAINT quiz_reviews_fk2 FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
);



CREATE TABLE quiz_reports (
    report_id INT NOT NULL AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    account_id INT NOT NULL,
    report_text TEXT NOT NULL,
    report_date DATETIME NOT NULL,
    CONSTRAINT quiz_reports_pk PRIMARY KEY (report_id),
    CONSTRAINT quiz_reports_fk1 FOREIGN KEY (quiz_id)
        REFERENCES quizzes (quiz_id),
    CONSTRAINT quiz_report_fk2 FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
);


insert into categories
 (category_name, category_image_url)
values
 ('Mathematics','https://ih1.redbubble.net/image.78509451.4399/flat,800x800,075,t.u1.jpg'),
 ('Computer Science','https://ih1.redbubble.net/image.78509451.4399/flat,800x800,075,t.u1.jpg'),
 ('History','https://ih1.redbubble.net/image.78509451.4399/flat,800x800,075,t.u1.jpg'),
 ('Biology','https://ih1.redbubble.net/image.78509451.4399/flat,800x800,075,t.u1.jpg'),
 ('Chemistry','https://ih1.redbubble.net/image.78509451.4399/flat,800x800,075,t.u1.jpg');

insert into question_types
	(question_type_name)
values
	('QuestionResponseQuestion'),
    ('FillTheBlankQuestion'),
    ('MultipleChoiceQuestion'),
    ('PictureResponseQuestion'),
    ('MultiAnswerQuestion'),
    ('MultipleChoiceMultipleAnswerQuestion'),
    ('MatchingQuestion');

    
CREATE TABLE admin_notifications(
	note_id INT NOT NULL AUTO_INCREMENT,
	note_header TEXT NOT NULL,
	note TEXT NOT NULL,
	CONSTRAINT admin_notifications_pk PRIMARY KEY (note_id)
);

