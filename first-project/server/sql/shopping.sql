DROP DATABASE IF EXISTS shopping;
CREATE DATABASE shopping;

\c shopping;

CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  category VARCHAR,
  model VARCHAR,
  maker VARCHAR,
  price NUMERIC(10,2)
);

INSERT INTO products (name, category, model, maker, price)
  VALUES ('Zenfone', 'Smartphone', 'Zenfone 3', 'Asus',259.99);
