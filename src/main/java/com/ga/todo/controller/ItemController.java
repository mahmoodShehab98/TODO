package com.ga.todo.controller;


import com.ga.todo.model.Item;
import com.ga.todo.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;

    @PostMapping("/categories/{categoryId}/items")
    public Item createItem(@PathVariable Long categoryId, @RequestBody Item item) {
        System.out.println("Calling createItem ==>");
        return itemService.createItem(categoryId, item);
    }

    @GetMapping("/categories/items/{itemId}")
    public Item getItem(@PathVariable Long itemId) {
        System.out.println("calling get Item");
        return itemService.getItem(itemId);
    }

    //GET
    @GetMapping("/categories/{categoryId}/items")
    public List<Item> getItems(@PathVariable Long categoryId) {
        System.out.println("Calling getCategory");

        return itemService.getAllItems();
    }

    //PUT
    @PutMapping("/categories/items/{itemId}")
    public Item updateItem(@PathVariable Long itemId, @RequestBody Item itemObject) {
        System.out.println("calling updateCategory ==>");
        return itemService.updateItem(itemId,itemObject);
    }

    //DELETE
    @DeleteMapping("/categories/items/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        System.out.println("calling updateCategory ==>");
        itemService.deleteItem(itemId);
    }



}
