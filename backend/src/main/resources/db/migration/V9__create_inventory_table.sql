CREATE TABLE IF NOT EXISTS inventory (
    itemId integer,
    itemName varchar(255),
    cost integer
);

ALTER TABLE inventory
    add constraint pk_inventory PRIMARY KEY (itemId);