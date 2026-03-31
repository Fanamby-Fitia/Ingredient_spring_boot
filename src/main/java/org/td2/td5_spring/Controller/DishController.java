package org.td2.td5_spring.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td5_spring.Entity.Dish;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Service.DishService;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody(required = false) List<Ingredient> ingredients
    ) {

        System.out.println("BODY = " + ingredients);

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
}
