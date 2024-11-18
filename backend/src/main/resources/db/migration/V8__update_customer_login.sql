ALTER TABLE customerLogin
    DROP CONSTRAINT chk_password_length;

ALTER TABLE customerLogin
    DROP CONSTRAINT chk_password_alphanumeric;