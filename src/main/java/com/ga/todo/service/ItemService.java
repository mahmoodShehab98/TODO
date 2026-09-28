package com.ga.todo.service;

import com.ga.todo.exception.InformationNotFoundException;
import com.ga.todo.model.Category;
import com.ga.todo.model.Item;
import com.ga.todo.model.User;
import com.ga.todo.repository.CategoryRepository;
import com.ga.todo.repository.ItemRepository;
import com.ga.todo.security.MyUserDetails;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    @Setter
    @Autowired
    private CategoryRepository categoryRepository;

    public Item createItem(Long categoryId, Item item) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "Category with id " + categoryId + " not found"
                        )
                );

        item.setCategory(category);

        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "Item with id " + itemId + " not found"
                        )
                );
    }

    public Item updateItem(Long itemId, Item item) {
        Item oldItem = getItem(itemId);

        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setDueDate(item.getDueDate());

        return itemRepository.save(oldItem);
    }

    public void deleteItem(Long itemId) {
        Item oldItem = getItem(itemId);

        itemRepository.delete(oldItem);
    }
}
