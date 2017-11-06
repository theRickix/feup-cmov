CREATE TYPE CARD_TYPE AS ENUM ('Visa','Maestro','MasterCard');

CREATE TABLE categories (
	id SERIAL PRIMARY KEY,
	name TEXT
);

CREATE TABLE makers (
	id SERIAL PRIMARY KEY,
	name TEXT
);

CREATE TABLE products ( 
  id SERIAL PRIMARY KEY,
  model TEXT,
  barcode TEXT,
  maker_id INTEGER REFERENCES makers(id) ON DELETE CASCADE,
  category_id INTEGER REFERENCES categories(id) ON DELETE CASCADE,
  price NUMERIC(10,2)
);

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name TEXT,
	username TEXT UNIQUE,
	email TEXT UNIQUE,
	password TEXT,
	fiscal TEXT UNIQUE,
	cc_type CARD_TYPE,
	cc_number TEXT,
	cc_validity DATE,
	public_key TEXT
);

CREATE TABLE purchases (
	id SERIAL PRIMARY KEY,
	purchase_date DATE,
	purchase_time TIME,
	user_id INTEGER REFERENCES users(id),
	validation_token UUID
);

CREATE TABLE purchase_rows (
	id SERIAL PRIMARY KEY,
	purchase_id INTEGER REFERENCES purchases(id),
	product_id INTEGER REFERENCES products(id),
	price NUMERIC(10,2),
	stock INTEGER
);

CREATE TABLE pictures (
	id SERIAL PRIMARY KEY,
	product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
	filename TEXT UNIQUE
);
