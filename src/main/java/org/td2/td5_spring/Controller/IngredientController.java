package org.td2.td5_spring.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.td5_spring.Entity.Ingredient;
import org.td2.td5_spring.Service.IngredientService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAll() {
        try {
            List<Ingredient> list = service.getAll();
            return ResponseEntity.ok(list);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getOne(@PathVariable Long id) {
        try {
            Ingredient ing = service.getOne(id);
            if (ing != null) return ResponseEntity.ok(ing);
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Double> getStock(
            @PathVariable Long id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {
        if (at == null || at.isEmpty() || unit == null || unit.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
        try {
            Double stock = service.stock(id, at, unit);
            if (stock == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(stock);

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}