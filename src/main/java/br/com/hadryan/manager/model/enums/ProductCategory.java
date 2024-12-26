package br.com.hadryan.manager.model.enums;

import lombok.Getter;

@Getter
public enum ProductCategory {

    CLOTHES("Clothes"),
    ELECTRONICS("Electronics"),
    FOOD("Food"),
    TOYS("Toys"),
    BOOKS("Books"),
    OTHERS("Others");

    private final String description;

    ProductCategory(String description) {
        this.description = description;
    }

}
