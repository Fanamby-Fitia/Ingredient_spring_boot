CREATE TABLE ingredient (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100),
                            category VARCHAR(50),
                            price DOUBLE PRECISION
);

CREATE TABLE dish (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100),
                      price DOUBLE PRECISION
);

CREATE TABLE dish_ingredient (
                                 dish_id INT,
                                 ingredient_id INT,
                                 PRIMARY KEY (dish_id, ingredient_id),
                                 FOREIGN KEY (dish_id) REFERENCES dish(id),
                                 FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);

CREATE TABLE stock_movement (
                                id SERIAL PRIMARY KEY,
                                ingredient_id INT,
                                quantity DOUBLE PRECISION,
                                unit VARCHAR(10),
                                movement_date TIMESTAMP,
                                FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);