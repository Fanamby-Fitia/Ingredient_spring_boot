package org.td2.td5_spring.Service;

import org.springframework.stereotype.Service;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Entity.StockMouvement;
import org.td2.td5_spring.Repository.ingredientRepository;

import java.util.List;

@Service
public class IngredientService {

    private ingredientRepository repo;

    public IngredientService(ingredientRepository repo) {
        this.repo = repo;
    }

    public List<Ingredient> getAll() {
        return repo.findAll();
    }

    public Ingredient getById(Long id) {
        return repo.findById(id);
    }

    public StockMouvement getStock(Long id, String at, String unit) {
        double value = repo.getStock(id, at, unit);

        StockMouvement s = new StockMouvement();
        s.setUnit(unit);
        s.setValue(value);
        return s;
    }
}