CREATE SEQUENCE IF NOT EXISTS inventory_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE inventory
(
    item_id   INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY ,
    item_name VARCHAR(255),
    cost      INTEGER,
    CONSTRAINT pk_inventory PRIMARY KEY (item_id)
);