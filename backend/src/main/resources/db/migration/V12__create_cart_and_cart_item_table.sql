CREATE TABLE cart (
                      cart_id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(255) NOT NULL,
                      FOREIGN KEY (username) REFERENCES customerlogin(username)
);

CREATE TABLE cart_item (
                           cart_item_id BIGSERIAL PRIMARY KEY,
                           cart_id BIGINT NOT NULL,
                           item_id INTEGER NOT NULL,
                           quantity INTEGER NOT NULL,
                           FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
                           FOREIGN KEY (item_id) REFERENCES inventory(item_id)
);

