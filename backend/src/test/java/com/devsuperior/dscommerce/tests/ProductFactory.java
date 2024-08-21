package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

import java.util.HashSet;

public class ProductFactory {

    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = Product.builder()
                .id(1L)
                .name("PC Gamer Turbo")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt")
                .price(99.90)
                .imgUrl("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/25-big.jpg")
                .categories(new HashSet<>())
                .build();
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }
}
