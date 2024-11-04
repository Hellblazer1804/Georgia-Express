CREATE SEQUENCE IF NOT EXISTS customer_seq START WITH 1 INCREMENT BY 50;

ALTER TABLE customer
    ADD credit_score INTEGER;

ALTER TABLE customer
    ADD customer_id INTEGER;

ALTER TABLE customer
    ADD phone_number VARCHAR(255);

ALTER TABLE customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (customer_id);

ALTER TABLE customer
    DROP COLUMN creditscore;

ALTER TABLE customer
    DROP COLUMN customerid;

ALTER TABLE customer
    DROP COLUMN phonenumber;

ALTER TABLE customer
    DROP COLUMN dob;

ALTER TABLE customer
    ADD dob TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE customer
    ALTER COLUMN ssn TYPE VARCHAR(255) USING (ssn::VARCHAR(255));