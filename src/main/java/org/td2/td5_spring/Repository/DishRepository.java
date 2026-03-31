package org.td2.td5_spring.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DishRepository {

    private JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM dish WHERE id=?",
                Integer.class,
                id
        );
        return count != null && count > 0;
    }

    public List<Dish> findAll() {

        String sql = "SELECT d.id as dish_id, d.name, d.price, " +
                "i.id as ing_id, i.name as ing_name, i.category, i.price as ing_price " +
                "FROM dish d " +
                "LEFT JOIN dish_ingredient di ON d.id=di.dish_id " +
                "LEFT JOIN ingredient i ON di.ingredient_id=i.id";

        Map<Long, Dish> map = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            Long dishId = rs.getLong("dish_id");

            Dish dish = map.get(dishId);
            if (dish == null) {
                dish = new Dish();
                dish.setId(dishId);
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getDouble("price"));
                dish.setIngredients(new ArrayList<>());
                map.put(dishId, dish);
            }

            Long ingId = rs.getLong("ing_id");
            if (ingId != 0) {
                Ingredient i = new Ingredient();
                i.setId((ingId));
                i.setName(rs.getString("ing_name"));
                i.setCategory(rs.getString("category"));
                i.setPrice(rs.getDouble("ing_price"));
                dish.getIngredients().add(i);
            }
        });

        return new ArrayList<>(map.values());
    }

    public void deleteIngredients(Long dishId) {
        jdbcTemplate.update("DELETE FROM dish_ingredient WHERE dish_id=?", dishId);
    }

    public void addIngredient(Long dishId, Long ingId) {
        jdbcTemplate.update(
                "INSERT INTO dish_ingredient(dish_id, ingredient_id) VALUES (?,?)",
                dishId, ingId
        );
    }
}