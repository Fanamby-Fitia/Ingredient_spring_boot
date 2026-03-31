package org.td2.td5_spring.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Ingredient ing = service.getById(id);

        if (ing == null) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        return ResponseEntity.ok(ing);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStock(
            @PathVariable Long id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {

        if (at == null || unit == null) {
            return ResponseEntity.badRequest()
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        Ingredient ing = service.getById(id);

        if (ing == null) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        return ResponseEntity.ok(service.getStock(id, at, unit));
    }
}