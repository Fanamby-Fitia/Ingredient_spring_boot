package org.td2.td5_spring.Service;

import org.springframework.stereotype.Service;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Repository.ingredientRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class IngredientService {

    private ingredientRepository repo;

    public IngredientService(ingredientRepository repo) {
        this.repo = repo;
    }

    public List<Ingredient> getAll() throws SQLException {
        return repo.findAll();
    }

    public Ingredient getOne(Long id) throws SQLException {
        return repo.findById(id);
    }

    public boolean exists(Long id) throws SQLException {
        return repo.existsById(id);
    }

    public double stock(Long id, String at, String unit) throws SQLException {
        return repo.getStock(id, at, unit);
    }

    public Ingredient create(Ingredient i) throws SQLException {
        return repo.save(i);
    }

    public void update(Ingredient i) throws SQLException {
        repo.update(i);
    }

    public void delete(Long id) throws SQLException {
        repo.delete(id);
    }
}