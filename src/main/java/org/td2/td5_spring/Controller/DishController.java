package org.td2.td5_spring.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Service.DishService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAll(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name
    ) throws SQLException {
        return ResponseEntity.ok(service.getFiltered(priceUnder, priceOver, name));
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(
            @PathVariable Long id,
            @RequestBody(required = false) List<Ingredient> ingredients
    ) throws SQLException {

        if (ingredients == null) {
            return ResponseEntity.badRequest().body("Request body is required");
        }

        if (!service.exists(id)) {
            return ResponseEntity.status(404)
                    .body("Dish.id=" + id + " is not found");
        }

        service.updateIngredients(id, ingredients);

        return ResponseEntity.ok("Updated");
    }

    @PostMapping
    public ResponseEntity<?> createDishes(@RequestBody List<Dish> dishes) throws SQLException {
        List<Dish> created = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getName() == null || d.getPrice() == null || d.getCategory() == null) {
                return ResponseEntity.badRequest()
                        .body("Dish name, category, and price are required");
            }
            if (service.existsByName(d.getName())) {
                return ResponseEntity.badRequest()
                        .body("Dish.name=" + d.getName() + " already exists");
            }

            try {
                created.add(service.create(d));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
        return ResponseEntity.status(201).body(created);
    }
}