-- liquibase formatted sql

-- changeset kfletch9:001.01 logicalfilepath:db/changelog/changes/001-bcu-txn-table.sql

CREATE TABLE transactions
(
    transaction_id VARCHAR(255) PRIMARY KEY,
    account_id     VARCHAR(255),
    date           DATE,
    name           VARCHAR(255),
    description    TEXT,
    check_number   VARCHAR(255),
    category       VARCHAR(255),
    amount         DECIMAL(10, 2),
    balance        DECIMAL(10, 2)
);
-- comment: This table will store all transactions from the bank account