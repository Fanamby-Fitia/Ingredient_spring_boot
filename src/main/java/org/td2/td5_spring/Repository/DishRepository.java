package org.td2.td5_spring.Repository;

import org.springframework.stereotype.Repository;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class DishRepository {

    private DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean existsById(Long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dish WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean existsByName(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dish WHERE name=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public List<Dish> findAll() throws SQLException {
        String sql = "SELECT d.id as dish_id, d.name, d.price, " +
                "i.id as ing_id, i.name as ing_name, i.category, i.price as ing_price " +
                "FROM dish d " +
                "LEFT JOIN dish_ingredient di ON d.id=di.dish_id " +
                "LEFT JOIN ingredient i ON di.ingredient_id=i.id";

        Map<Long, Dish> map = new HashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
                    i.setId(ingId);
                    i.setName(rs.getString("ing_name"));
                    i.setCategory(rs.getString("category"));
                    i.setPrice(rs.getDouble("ing_price"));
                    dish.getIngredients().add(i);
                }
            }
        }

        return new ArrayList<>(map.values());
    }

    public void deleteIngredients(Long dishId) throws SQLException {
        String sql = "DELETE FROM dish_ingredient WHERE dish_id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, dishId);
            ps.executeUpdate();
        }
    }

    public void addIngredient(Long dishId, Long ingId) throws SQLException {
        String sql = "INSERT INTO dish_ingredient(dish_id, ingredient_id) VALUES (?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, dishId);
            ps.setLong(2, ingId);
            ps.executeUpdate();
        }
    }

    public Dish save(Dish dish) throws SQLException {
        String sql = "INSERT INTO dish(name, category, price) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dish.getName());
            ps.setString(2, dish.getCategory());
            ps.setDouble(3, dish.getPrice());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dish.setId(rs.getLong(1));
                }
            }
        }
        return dish;
    }
}