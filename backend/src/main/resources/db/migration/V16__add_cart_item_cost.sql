ALTER TABLE cart_item
    ADD cost INTEGER;

ALTER TABLE cart_item
    ALTER COLUMN cost SET NOT NULL;