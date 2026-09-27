package com.ga.todo.repository;


import com.ga.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String emailAddress);

    User findUserByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String attr0);
}
