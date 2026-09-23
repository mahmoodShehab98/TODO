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
import java.util.Optional;

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

    //    UPDATE
    public Category updateCategory(Long categoryId, @RequestBody Category categoryObject){
        System.out.println("calling updateCategory ==> ");
        Optional<Category> category= categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            if (categoryObject.getName().equals(category.get().getName())) {
                System.out.println("same");
                throw new InformationExistException("category " + category.get().getName() + "is already exists");

            } else {
                Category updateCategory = categoryRepository.findById(categoryId).get();
                updateCategory.setName(categoryObject.getName());
                updateCategory.setDescription(categoryObject.getDescription());
                return categoryRepository.save(updateCategory);
            }
        }else {
            throw new InformationNotFoundException("Category with id " + category + "not found");


        }
    }

//        DELETE

    public Optional<Category> deleteCategory(Long categoryid){
        System.out.println("service calling deleteCategory ==> ");
        Optional<Category> category = categoryRepository.findById(categoryid);

        if (category.isPresent()){
            categoryRepository.deleteById(categoryid);
            return category;
        }else {
            throw new InformationNotFoundException("Category with id " + categoryid + "not found");
        }
    }





}