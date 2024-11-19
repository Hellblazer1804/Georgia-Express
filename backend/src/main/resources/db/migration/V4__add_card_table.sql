CREATE TABLE IF NOT EXISTS card(
    card_number INTEGER,
    expirationDate DATE,
    cvv INTEGER,
    creditLimit NUMERIC(12, 2),
    cardBalance NUMERIC(12, 2),
    minimumPayment NUMERIC(8, 2),
    rewardPoints NUMERIC(10, 2),
    customer_id INTEGER,
    recommendedCreditLimit DOUBLE PRECISION,
    verificationReason VARCHAR(255),
    cardStatus VARCHAR(20),
    isApproved BOOLEAN,
    result VARCHAR(255)
);