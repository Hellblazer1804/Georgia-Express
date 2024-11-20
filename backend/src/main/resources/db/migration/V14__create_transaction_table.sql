CREATE TABLE transactions (
                              transaction_id BIGSERIAL PRIMARY KEY,
                              username VARCHAR(255) NOT NULL,
                              amount DECIMAL(10, 2) NOT NULL,
                              transaction_date VARCHAR(255) NOT NULL,
                              FOREIGN KEY (username) REFERENCES customerlogin(username)
);
