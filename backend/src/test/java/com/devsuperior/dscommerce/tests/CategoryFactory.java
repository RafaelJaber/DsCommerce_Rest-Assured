package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.Category;

public class CategoryFactory {

    public static Category createCategory() {
        return Category.builder()
                .id(1L)
                .name("Games")
                .build();
    }

    public static Category createCategory(Long id, String name) {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }
}
