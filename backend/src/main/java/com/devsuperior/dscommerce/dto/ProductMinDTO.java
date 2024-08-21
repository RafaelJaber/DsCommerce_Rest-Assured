package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imageUrl;

    public ProductMinDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImgUrl();
    }
}
