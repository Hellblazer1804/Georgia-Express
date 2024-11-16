CREATE TABLE IF NOT EXISTS customerLogin(
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    customer_id INTEGER
);