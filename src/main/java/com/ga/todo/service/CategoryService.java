package com.ga.todo.service;

import com.ga.todo.exception.InformationExistException;
import com.ga.todo.exception.InformationNotFoundException;
import com.ga.todo.model.Category;
import com.ga.todo.model.User;
import com.ga.todo.repository.CategoryRepository;
import com.ga.todo.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final String UPLOAD_DIR = "uploads/";

    private CategoryRepository categoryRepository;

    private User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) (SecurityContextHolder.getContext()
                .getAuthentication()).getPrincipal();
        assert userDetails != null;
        return userDetails.getUser();
    }

    public Category createCategory(
            String name,
            String description,
            MultipartFile image) {

        System.out.println("Service Calling createCategory ==> ");

        Category category = categoryRepository.findByUserIdAndName(getCurrentLoggedInUser().getId(),name);

        if (category != null) {
            throw new InformationExistException(
                    "category with name " + category.getName() + " already exists"
            );
        }

        Category newCategory = new Category();

        newCategory.setName(name);
        newCategory.setDescription(description);
        newCategory.setUser(getCurrentLoggedInUser());

        try {

            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Get original filename
            String originalFileName = image.getOriginalFilename();

            // Generate unique ID
            String uniqueId = UUID.randomUUID().toString();

            // Create unique filename
            String fileName = uniqueId + "_" + originalFileName;

            // Create file path
            Path filePath = uploadPath.resolve(fileName);

            // Save image
            image.transferTo(filePath);

            // Save image path in database
            newCategory.setImageUrl(UPLOAD_DIR + fileName);

        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }

        return categoryRepository.save(newCategory);
    }

    public List<Category> getCategories() {
        System.out.println("Service calling getCategories ==>");
        return categoryRepository.findByUserId(getCurrentLoggedInUser().getId());
    }

    public Category getCategory(Long categoryId) {
        System.out.println("service getCategory ==>");

        return categoryRepository.findByUserIdAndId(getCurrentLoggedInUser().getId(),categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "category with id " + categoryId + " not found"
                        )
                );
    }


    public Category updateCategory(Long categoryId, Category categoryObject) {
        System.out.println("service calling updateCategory ==>");

        Category existingCategory = categoryRepository.findByUserIdAndId(getCurrentLoggedInUser().getId(),categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "category with id " + categoryId + " not found"
                        )
                );

        existingCategory.setName(categoryObject.getName());
        existingCategory.setDescription(categoryObject.getDescription());

        return categoryRepository.save(existingCategory);
    }


    public Category deleteCategory(Long categoryId) {
        System.out.println("service calling deleteCategory ==>");

        Category category = categoryRepository.findByIdAndUserId(getCurrentLoggedInUser().getId(), categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "category with id " + categoryId + " not found"
                        )
                );

        categoryRepository.delete(category);
        return category;
    }
}