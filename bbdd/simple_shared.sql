CREATE DATABASE simple_shared;
USE simple_shared;

CREATE TABLE friend (
    id_friend BIGINT PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE expense (
    id_expense BIGINT PRIMARY KEY,
    id_friend BIGINT,
    amount DECIMAL,
    description VARCHAR(50),
    expense_date DATETIME,
    FOREIGN KEY (id_friend) REFERENCES friend(id_friend) ON DELETE CASCADE 
);
