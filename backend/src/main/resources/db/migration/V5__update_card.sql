ALTER TABLE card
    ADD CONSTRAINT pk_card PRIMARY KEY (cardNumber);

ALTER TABLE card
    ADD CONSTRAINT fk_card FOREIGN KEY (customer_id) REFERENCES customer (customer_id);