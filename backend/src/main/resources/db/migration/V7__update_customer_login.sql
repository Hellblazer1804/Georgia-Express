ALTER TABLE customerLogin
    ADD CONSTRAINT pk_customerLogin PRIMARY KEY (username);

ALTER TABLE customerLogin
    ADD CONSTRAINT fk_card FOREIGN KEY (customer_id) REFERENCES customer (customer_id);

ALTER TABLE customerLogin
    ADD CONSTRAINT chk_password_length CHECK (LENGTH(password) BETWEEN 8 AND 12);

ALTER TABLE customerLogin
    ADD CONSTRAINT chk_password_alphanumeric CHECK (password ~ '^[a-zA-Z0-9]{8,12}$');