CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  category VARCHAR,
  model VARCHAR,
  maker VARCHAR,
  price NUMERIC(10,2)
);
