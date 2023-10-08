-- INSERT INTO products (name, description, price, quantity) VALUES ('Iphone', 'Latest model of the popular smartphone', 999.99, 50);
-- INSERT INTO products (name, description, price, quantity) VALUES ('Samsung Smart TV', '55-inch 4K UHD Smart TV with built-in streaming apps', 699.99, 30);
-- INSERT INTO products (name, description, price, quantity) VALUES ('Asus Laptop', 'Powerful laptop with Intel Core i7 processor and 16GB RAM', 899.99, 20);
-- INSERT INTO products (name, description, price, quantity) VALUES ('Playstation 5', 'Next-gen gaming console with 4K graphics support', 499.99, 40);

-- Insert 'Iphone' if it doesn't exist
INSERT INTO products (name, description, price, quantity)
SELECT 'Iphone', 'Latest model of the popular smartphone', 999.99, 50
WHERE NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Iphone'
);

-- Insert 'Samsung Smart TV' if it doesn't exist
INSERT INTO products (name, description, price, quantity)
SELECT 'Samsung Smart TV', '55-inch 4K UHD Smart TV with built-in streaming apps', 699.99, 30
WHERE NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Samsung Smart TV'
);

-- Insert 'Asus Laptop' if it doesn't exist
INSERT INTO products (name, description, price, quantity)
SELECT 'Asus Laptop', 'Powerful laptop with Intel Core i7 processor and 16GB RAM', 899.99, 20
WHERE NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Asus Laptop'
);

-- Insert 'Playstation 5' if it doesn't exist
INSERT INTO products (name, description, price, quantity)
SELECT 'Playstation 5', 'Next-gen gaming console with 4K graphics support', 499.99, 40
WHERE NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Playstation 5'
);
