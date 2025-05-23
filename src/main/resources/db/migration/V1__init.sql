CREATE TABLE accounts
(
    id             VARCHAR(36) PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance        DECIMAL(19, 2)     NOT NULL
);

CREATE TABLE transactions
(
    id           VARCHAR(36) PRIMARY KEY,
    account_id   VARCHAR(36)    NOT NULL,
    payment_type VARCHAR(1)     NOT NULL,
    amount       DECIMAL(19, 2) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts (id)
);
