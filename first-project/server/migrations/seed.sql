INSERT INTO
	categories(name)
VALUES
	('Smartphone'),('Smartwatch'),('Tablet'),('Desktop'),('Laptop'),('Smart TV'),('VR Glasses'),
	('Headphone'),('Camera'),('Mouse'),('Keyboard'),('Charger');

INSERT INTO
	makers(name)
VALUES
	('Sony'),('Samsung'),('Apple'),('Huawei'),('Motorola'),('Asus'),('Acer');


INSERT INTO
	products(model, barcode, maker_id, category_id, price)
 VALUES 
 	('Zenfone 3','638586874977', 6, 1, 259.99),
 	('iPhone 8','190198453655', 3, 1, 869.99);
