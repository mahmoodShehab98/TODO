package com.ga.todo.controller;

import com.ga.todo.model.Category;
import com.ga.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // CRUD
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        return categoryService.createCategory(categoryObject);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("calling get categories");
        return categoryService.getCategories();
    }

    @GetMapping("/category/{id}")
    public Category getCategories(@PathVariable Long id) {
        System.out.println("Calling getCategory");

        return categoryService.getCategory(id);
    }

}