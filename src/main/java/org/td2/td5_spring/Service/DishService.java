package org.td2.td5_spring.Service;

import org.springframework.stereotype.Service;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Repository.DishRepository;
import org.td2.td5_spring.Repository.ingredientRepository;

import java.util.List;

@Service
public class DishService {

    private DishRepository dishRepo;
    private ingredientRepository ingRepo;

    public DishService(DishRepository dishRepo, ingredientRepository ingRepo) {
        this.dishRepo = dishRepo;
        this.ingRepo = ingRepo;
    }

    public List<Dish> getAll() {
        return dishRepo.findAll();
    }

    public boolean exists(Long id) {
        return dishRepo.existsById(id);
    }

    public void updateIngredients(Long dishId, List<Ingredient> ingredients) {

        dishRepo.deleteIngredients(dishId);

        ingredients.stream()
                .filter(i -> i.getId() != null)
                .filter(i -> ingRepo.existsById(i.getId()))
                .forEach(i -> dishRepo.addIngredient(dishId, i.getId()));
    }
}