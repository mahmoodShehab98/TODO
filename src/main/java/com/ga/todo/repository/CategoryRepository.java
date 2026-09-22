package com.ga.todo.repository;

import com.ga.todo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // the method must be identical findByName
    Category findByName(String categoryName);

}
