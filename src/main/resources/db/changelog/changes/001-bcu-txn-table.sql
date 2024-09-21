CREATE TABLE transactions
(
    transaction_id VARCHAR(255) PRIMARY KEY,
    date           DATE,
    name           VARCHAR(255),
    description    TEXT,
    check_number   VARCHAR(255),
    category       VARCHAR(255),
    amount         DECIMAL(10, 2),
    balance        DECIMAL(10, 2)
);