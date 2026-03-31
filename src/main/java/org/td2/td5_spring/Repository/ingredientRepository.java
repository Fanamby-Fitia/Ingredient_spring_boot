package org.td2.td5_spring.Repository;

import org.springframework.stereotype.Repository;
import org.td2.td5_spring.Entity.Ingredient;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ingredientRepository {

    private final DataSource dataSource;

    public ingredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAll() throws SQLException {
        String sql = "SELECT id, name, category, price FROM ingredient";
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category")
                ));
            }
        }

        return ingredients;
    }

    public Ingredient findById(Long id) throws SQLException {
        String sql = "SELECT id, name, category, price FROM ingredient WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ingredient(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("category")
                    );
                }
            }
        }

        return null;
    }

    public boolean existsById(Long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ingredient WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public double getStock(Long id, String at, String unit) throws SQLException {
        String sql = "SELECT COALESCE(SUM(quantity),0) FROM stock_movement " +
                "WHERE ingredient_id=? AND unit=? AND movement_date<=?";

        Timestamp timestamp;
        try {
            LocalDate date = LocalDate.parse(at);
            timestamp = Timestamp.valueOf(date.atTime(23, 59, 59)); // fin de journée
        } catch (Exception e) {
            throw new IllegalArgumentException("Le format de la date doit être yyyy-MM-dd", e);
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.setString(2, unit);
            ps.setTimestamp(3, timestamp);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }

        return 0;
    }

    public Ingredient save(Ingredient ingredient) throws SQLException {
        String sql = "INSERT INTO ingredient(name, category, price) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ingredient.getName());
            ps.setString(2, ingredient.getCategory());
            ps.setDouble(3, ingredient.getPrice());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ingredient.setId(rs.getLong(1));
                }
            }
        }

        return ingredient;
    }

    public void update(Ingredient ingredient) throws SQLException {
        String sql = "UPDATE ingredient SET name=?, category=?, price=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ingredient.getName());
            ps.setString(2, ingredient.getCategory());
            ps.setDouble(3, ingredient.getPrice());
            ps.setLong(4, ingredient.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}