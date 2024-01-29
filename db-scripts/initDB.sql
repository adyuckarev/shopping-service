CREATE TABLE purchase
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX purchase_unique_name_idx ON purchase (name);

CREATE TABLE user_purchase_info
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    lastname      VARCHAR(255) NOT NULL,
    age           INTEGER CHECK (age >= 1 AND age <= 100),
    purchase_id   INTEGER      NOT NULL,
    count         INTEGER CHECK (count >= 1 AND count <= 100),
    amount        DOUBLE PRECISION,
    purchase_date DATE         NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchase (id)
);
CREATE INDEX user_purchase_info_id_idx ON user_purchase_info (purchase_id);