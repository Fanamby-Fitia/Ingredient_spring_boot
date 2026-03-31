-- INGREDIENTS
INSERT INTO ingredient(name, category, price) VALUES
                                                  ('Tomate', 'Legume', 2.5),
                                                  ('Fromage', 'Laitier', 5.0),
                                                  ('Poulet', 'Viande', 10.0),
                                                  ('Riz', 'Cereale', 3.0);

-- DISHES
INSERT INTO dish(name, category, price) VALUES
                                  ('Pizza', 'Plat principal', 20.0),
                                  ('Poulet Riz', 'Plat principal', 15.0);

-- RELATIONS
INSERT INTO dish_ingredient(dish_id, ingredient_id) VALUES
                                                        (1,1),
                                                        (1,2),
                                                        (2,3),
                                                        (2,4);

-- STOCK
INSERT INTO stock_movement(ingredient_id, quantity, unit, movement_date) VALUES
                                                                             (1, 100, 'KG', '2024-01-01'),
                                                                             (2, 50, 'KG', '2024-01-01'),
                                                                             (3, 30, 'KG', '2024-01-01'),
                                                                             (4, 200, 'KG', '2024-01-01');