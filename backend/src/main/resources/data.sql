
INSERT INTO users (email, password, firstname, lastname)
VALUES
    ('john.doe@example.com', 'password1', 'John', 'Doe'),
    ('alice.smith@example.com', 'password2', 'Alice', 'Smith')
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO stores (name, user_id)
VALUES
    ('Store A', 1),
    ('Store B', 1),
    ('Store C', 2)
ON DUPLICATE KEY UPDATE name = VALUES(name), user_id = VALUES(user_id);


-- Product Data

-- Insert 'Iphone' if it doesn't exist
INSERT INTO products (name, description, price, quantity, store_id)
SELECT 'Iphone', 'Latest model of the popular smartphone', 999.99, 50, s.id
FROM stores s
WHERE s.name = 'Store A'
  AND NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Iphone' AND store_id = s.id
);

-- Insert 'Samsung Smart TV' if it doesn't exist
INSERT INTO products (name, description, price, quantity, store_id)
SELECT 'Samsung Smart TV', '55-inch 4K UHD Smart TV with built-in streaming apps', 699.99, 30, s.id
FROM stores s
WHERE s.name = 'Store A'
  AND NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Samsung Smart TV' AND store_id = s.id
);

-- Insert 'Asus Laptop' if it doesn't exist
INSERT INTO products (name, description, price, quantity, store_id)
SELECT 'Asus Laptop', 'Powerful laptop with Intel Core i7 processor and 16GB RAM', 899.99, 20, s.id
FROM stores s
WHERE s.name = 'Store A'
  AND NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Asus Laptop' AND store_id = s.id
);

-- Insert 'Playstation 5' if it doesn't exist
INSERT INTO products (name, description, price, quantity, store_id)
SELECT 'Playstation 5', 'Next-gen gaming console with 4K graphics support', 499.99, 40, s.id
FROM stores s
WHERE s.name = 'Store A'
  AND NOT EXISTS (
    SELECT 1 FROM products WHERE name = 'Playstation 5' AND store_id = s.id
);

