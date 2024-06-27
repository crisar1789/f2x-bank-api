CREATE TABLE Transaction_Type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code ENUM('W', 'P', 'T'),
	name VARCHAR(20)
);
---
INSERT INTO `f2x_bank`.`Transaction_Type`
(
`code`,
`name`)
VALUES
('W',
'Withdraw'),
('P',
'Pay'),
('T',
'Transfer');

------------------------------------------------

CREATE TABLE Account_Type (
    id INT AUTO_INCREMENT PRIMARY KEY,
	code ENUM('AH', 'CO'),
    name VARCHAR(20)
);
---
INSERT INTO `f2x_bank`.`account_type`
(
`code`,
`name`)
VALUES
('AH',
'Ahorros'),
('CO',
'Corriente');

------------------------------------------------

CREATE TABLE State (
    id INT AUTO_INCREMENT PRIMARY KEY,
	code ENUM('A', 'I', 'C'),
    name VARCHAR(20)
);
---
INSERT INTO `f2x_bank`.`state`
(
`code`,
`name`)
VALUES
('A',
'Active'),
('I',
'Inactive'),
('C',
'Cancel');

------------------------------------------------

CREATE TABLE Document_Type (
    id INT AUTO_INCREMENT PRIMARY KEY,
	code ENUM('CC', 'NIT', 'CE'),
    name VARCHAR(30)
);
---
INSERT INTO `f2x_bank`.`document_type`
(
`code`,
`name`)
VALUES
('CC',
'Cédula de Ciudadanía'),
('NIT',
'Número de Identificación'),
('CE',
'Cédula de Extranjería');

------------------------------------------------

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identification_type_id INT NOT NULL,
    identification_number VARCHAR(15) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	FOREIGN KEY (identification_type_id) REFERENCES Document_Type(id)
);

CREATE TABLE Product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_type_id INT,
    account_number VARCHAR(11) NOT NULL,
    state_id INT NOT NULL DEFAULT 1,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0,
    gmf_exempt BOOLEAN NOT NULL DEFAULT 1,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    FOREIGN KEY (account_type_id) REFERENCES Account_Type(id),
	FOREIGN KEY (state_id) REFERENCES State(id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Transaction (
	id INT AUTO_INCREMENT PRIMARY KEY,
	transaction_type_id INT,
	account_origin_id INT,
	account_destination_id INT,
	value DECIMAL(15, 2) NOT NULL,
	transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (account_origin_id) REFERENCES Product(id),
	FOREIGN KEY (account_destination_id) REFERENCES Product(id),
    FOREIGN KEY (transaction_type_id) REFERENCES Transaction_Type(id)
);