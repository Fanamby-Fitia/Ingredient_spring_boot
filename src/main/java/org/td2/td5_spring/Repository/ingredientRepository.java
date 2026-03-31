package org.td2.td5_spring.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.td2.td5_spring.Entity.Ingredient;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ingredientRepository {

    private JdbcTemplate jdbcTemplate;

    public ingredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM ingredient", (rs, i) -> {
            Ingredient ing = new Ingredient();
            ing.setId((long) rs.getInt("id"));
            ing.setName(rs.getString("name"));
            ing.setCategory(rs.getString("category"));
            ing.setPrice(rs.getDouble("price"));
            return ing;
        });
    }

    public Ingredient findById(Long id) {
        List<Ingredient> list = jdbcTemplate.query(
                "SELECT * FROM ingredient WHERE id = ?",
                new Object[]{id},
                (rs, i) -> {
                    Ingredient ing = new Ingredient();
                    ing.setId((long) rs.getInt("id"));
                    ing.setName(rs.getString("name"));
                    ing.setCategory(rs.getString("category"));
                    ing.setPrice(rs.getDouble("price"));
                    return ing;
                });

        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ingredient WHERE id = ?",
                Integer.class,
                id
        );
        return count != null && count > 0;
    }

    public double getStock(Long id, String at, String unit) {

        String sql = "SELECT COALESCE(SUM(quantity),0) FROM stock_movement " +
                "WHERE ingredient_id=? AND unit=? AND movement_date<=?";

        Timestamp timestamp = Timestamp.valueOf(at + " 00:00:00");

        Double value = jdbcTemplate.queryForObject(
                sql,
                Double.class,
                id, unit, timestamp
        );

        return value != null ? value : 0;
    }
}