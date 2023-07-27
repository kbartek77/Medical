--CREATE TABLE IF NOT EXISTS patient (
--ID INT AUTO_INCREMENT PRIMARY KEY,
--FIRST_NAME VARCHAR(100) NOT NULL,
--LAST_NAME VARCHAR(100) NOT NULL,
--PASSWORD VARCHAR(100) NOT NULL,
--EMAIL VARCHAR(100) NOT NULL,
--BIRTHDAY VARCHAR(100) NOT NULL,
--ID_CARD_NO VARCHAR(100) NOT NULL,
--PHONE_NUMBER VARCHAR(100) NOT NULL
--);
--CREATE TABLE IF NOT EXISTS visit (
--ID INT AUTO_INCREMENT PRIMARY KEY,
--DATE_TIME TIMESTAMP NOT NULL,
--PATIENT_ID INT,
--FOREIGN KEY (PATIENT_ID) REFERENCES patient (ID)
--);
--CREATE TABLE IF NOT EXISTS doctor(
--ID INT AUTO_INCREMENT PRIMARY KEY,
--FIRST_NAME VARCHAR(100) NOT NULL,
--LAST_NAME VARCHAR(100) NOT NULL,
--EMAIL VARCHAR(100) NOT NULL,
--PASSWORD VARCHAR(100) NOT NULL,
--SPECIALIZATION VARCHAR(100) NOT NULL
--);

INSERT INTO patient (EMAIL, PASSWORD, ID_CARD_NO, FIRST_NAME, LAST_NAME, PHONE_NUMBER, BIRTHDAY)
VALUES ('john.doe@example.com', 'password123', '1234214', 'John', 'Doe', '123456789', '1990-05-15');

INSERT INTO patient (EMAIL, PASSWORD, ID_CARD_NO, FIRST_NAME, LAST_NAME, PHONE_NUMBER, BIRTHDAY)
VALUES ('jane.smith@example.com', 'abc123', '21341442', 'Jane', 'Smith', '13451355', '1985-09-23');

INSERT INTO visit (DATE_TIME, END_DATE_TIME, PATIENT_ID)
VALUES ('2023-10-12 10:00:00', '2023-10-12 11:00:00', 1);

INSERT INTO visit (DATE_TIME, END_DATE_TIME, PATIENT_ID)
VALUES ('2023-10-13 14:30:00','2023-10-13 15:30:00', 2);

INSERT INTO visit (DATE_TIME, END_DATE_TIME)
VALUES ('2024-07-13 14:30:00','2024-07-13 15:30:00');

INSERT INTO hospital (NAME, TOWN, POSTAL_CODE, STREET, NUMBER_OF_BUILDING)
VALUES ('Hospital A', 'Town A', '12345', 'Main Street', '123');

INSERT INTO hospital (NAME, TOWN, POSTAL_CODE, STREET, NUMBER_OF_BUILDING)
VALUES ('Hospital B', 'Town B', '67890', 'Second Street', '456');

INSERT INTO doctor (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SPECIALIZATION, HOSPITAL_ID)
VALUES ('james.smith@example.com', 'doctor123', 'James', 'Smith', 'Cardiology', 1L);

INSERT INTO doctor (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, SPECIALIZATION, HOSPITAL_ID)
VALUES ('emily.johnson@example.com', 'doctor456', 'Emily', 'Johnson', 'Dermatology', 2L);
