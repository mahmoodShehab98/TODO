package com.ga.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// Having those
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Getter
//@Setter

// Equals
@Data // It generates all setters, getters, toString, and Constructors

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "category", orphanRemoval = true)
    private List<Item> itemList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private com.ga.todo.model.User user;

}

