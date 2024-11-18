CREATE TABLE if not exists storefront_transaction (
    transactionNumber INTEGER,
    username VARCHAR(255),
    itemBoughtId INTEGER
);

ALTER TABLE storefront_transaction
    ADD CONSTRAINT pk_store_front PRIMARY KEY (transactionNumber);

ALTER TABLE storefront_transaction
    ADD CONSTRAINT fk_login FOREIGN KEY (username) REFERENCES customerlogin(username);

ALTER TABLE storefront_transaction
    ADD CONSTRAINT fk_item FOREIGN KEY (itemBoughtId) REFERENCES inventory(itemId);