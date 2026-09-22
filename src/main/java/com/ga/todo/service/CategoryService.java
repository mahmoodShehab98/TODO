package com.ga.todo.service;

import com.ga.todo.exception.InformationExistException;
import com.ga.todo.exception.InformationNotFoundException;
import com.ga.todo.model.Category;
import com.ga.todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // CRUD
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject) {
        System.out.println("Calling createCategory ==> ");

        Category category = categoryRepository.findByName(categoryObject.getName());
        if (category != null) {
            throw new InformationExistException("category with name" + category.getName() + " already exists");
        } else {
            return categoryRepository.save(categoryObject);
        }
    }

    public List<Category> getCategories(){
        System.out.println("Service calling get categories");

        return  categoryRepository.findAll();
    }

    public Category getCategory(Long id){
        System.out.println("Service calling get category");

        return  categoryRepository.findById(id).orElseThrow(()->new InformationNotFoundException("Category Not Found"));
    }




}