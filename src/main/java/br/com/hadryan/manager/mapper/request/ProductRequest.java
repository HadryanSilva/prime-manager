package br.com.hadryan.manager.mapper.request;

import br.com.hadryan.manager.model.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String barcode;

    private String name;

    private String brand;

    private String description;

    private Double buyPrice;

    private Double sellPrice;

    private Integer stockQuantity;

    private ProductCategory category;
}
