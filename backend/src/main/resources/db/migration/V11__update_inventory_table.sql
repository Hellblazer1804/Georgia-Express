CREATE SEQUENCE IF NOT EXISTS inventory_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS transaction_seq START WITH 1 INCREMENT BY 50;

ALTER TABLE storefront_transaction DROP CONSTRAINT fk_item;
ALTER TABLE storefront_transaction DROP COLUMN transactionnumber;
ALTER TABLE storefront_transaction ADD COLUMN transaction_number INTEGER;
ALTER TABLE storefront_transaction DROP COLUMN itemboughtid;
ALTER TABLE storefront_transaction ADD COLUMN item_bought_id INTEGER;

ALTER TABLE inventory DROP COLUMN itemid;

ALTER TABLE inventory DROP COLUMN itemname;

ALTER TABLE inventory ADD COLUMN item_id INTEGER;

ALTER TABLE inventory ADD COLUMN item_name VARCHAR(255);


ALTER TABLE inventory ADD CONSTRAINT pk_inventory PRIMARY KEY (item_id);
