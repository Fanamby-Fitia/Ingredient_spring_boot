package org.td2.td5_spring.Service;

import org.springframework.stereotype.Service;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Repository.DishRepository;
import org.td2.td5_spring.Repository.ingredientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    private DishRepository dishRepo;
    private ingredientRepository ingRepo;

    public DishService(DishRepository dishRepo, ingredientRepository ingRepo) {
        this.dishRepo = dishRepo;
        this.ingRepo = ingRepo;
    }

    public List<Dish> getAll() throws SQLException {
        return dishRepo.findAll();
    }

    public List<Dish> getFiltered(Double priceUnder, Double priceOver, String name) throws SQLException {
        return getAll().stream()
                .filter(d -> priceUnder == null || d.getPrice() < priceUnder)
                .filter(d -> priceOver == null || d.getPrice() > priceOver)
                .filter(d -> name == null || d.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean exists(Long id) throws SQLException {
        return dishRepo.existsById(id);
    }

    public boolean existsByName(String name) throws SQLException {
        return dishRepo.existsByName(name);
    }

    public void updateIngredients(Long dishId, List<Ingredient> ingredients) throws SQLException {
        dishRepo.deleteIngredients(dishId);

        for (Ingredient i : ingredients) {
            if (i.getId() != null && ingRepo.existsById(i.getId())) {
                dishRepo.addIngredient(dishId, i.getId());
            }
        }
    }

    public Dish create(Dish dish) throws SQLException {
        return dishRepo.save(dish);
    }
}