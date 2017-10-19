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
	products(model, maker_id, category_id, price)
 VALUES 
 	('Zenfone 3', 1, 6,259.99);

INSERT INTO
	users(name,nickname,email,password)
VALUES
	('Ricardo Magalhães','theRickix','ricardo_magalhaes45@hotmail.com','teste');
