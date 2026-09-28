package com.ga.todo.repository;

import com.ga.todo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // the method must be identical findByName
    Category findByUserIdAndName(Long userId, String categoryName);
    Optional<Category> findByUserIdAndId(Long userId, Long categoryId);
    Category findByName(String categoryName);
    List<Category> findByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long id,Long userId);

}
