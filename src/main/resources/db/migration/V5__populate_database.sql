INSERT INTO categories (name)
VALUES ('Fruits'),
       ('Vegetables'),
       ('Dairy'),
       ('Bakery'),
       ('Meat'),
       ('Seafood'),
       ('Snacks'),
       ('Beverages'),
       ('Frozen Foods'),
       ('Pantry Staples');

INSERT INTO products (name, price, description, category_id)
VALUES ('Bananas', 1.29, 'Fresh organic bananas, approx. 1 lb', 1),
       ('Broccoli', 2.49, 'Fresh green broccoli, locally grown', 2),
       ('Whole Milk', 3.99, '1 gallon of whole milk from grass-fed cows', 3),
       ('Sourdough Bread', 4.50, 'Artisan sourdough loaf, baked fresh daily', 4),
       ('Chicken Breast', 7.99, 'Boneless, skinless chicken breasts, 1 lb', 5),
       ('Atlantic Salmon Fillet', 12.49, 'Fresh farm-raised Atlantic salmon, 1 lb', 6),
       ('Potato Chips - Sea Salt', 2.99, 'Kettle-cooked chips with sea salt flavor, 8 oz bag', 7),
       ('Orange Juice', 4.25, '100% pure squeezed orange juice, 1 liter', 8),
       ('Frozen Pizza - Margherita', 6.75, 'Thin-crust frozen pizza with tomato and mozzarella', 9),
       ('Pasta - Spaghetti', 1.50, 'Durum wheat spaghetti pasta, 500g', 10);
