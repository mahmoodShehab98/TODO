package com.ga.todo.controller;

import com.ga.todo.exception.InformationExistException;
import com.ga.todo.model.Category;
import com.ga.todo.repository.CategoryRepository;
import com.ga.todo.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    // CRUD
    // C - Create - HTTP POST - To create a record (category)
    @PostMapping("/categories")
    public Category createCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        System.out.println("Calling createCategory ==> ");
//        System.out.println("Object ==> "+ name + description);

        return categoryService.createCategory(name, description, image);
    }


    // R - Read - HTTP GET - To read all records / record by id
    @GetMapping("/categories")
    public List<Category> getCategories() {
        System.out.println("calling getCategories() ==> ");
        return categoryService.getCategories();
    }

    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        System.out.println("calling getCategory ==>");
        return categoryService.getCategory(categoryId);
    }

    // U - Update - HTTP PUT - To update a record
    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject) {
        System.out.println("calling updateCategory ==>");
        return categoryService.updateCategory(categoryId, categoryObject);
    }

    // D -Delete - HTTP DELETE - To remove a record
    @DeleteMapping("/categories/{categoryId}")
    public Category deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        System.out.println("calling deleteCategory ==>");
        return categoryService.deleteCategory(categoryId);
    }


}