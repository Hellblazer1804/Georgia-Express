CREATE TABLE IF NOT EXISTS card(
    cardNumber INTEGER,
    ExpirationDate DATE,
    cvv INTEGER,
    creditLimit NUMERIC(12, 2),
    cardBalance NUMERIC(12, 2),
    minimumPayment NUMERIC(8, 2),
    rewardPoints NUMERIC(10, 2),
    customer_id INTEGER
);