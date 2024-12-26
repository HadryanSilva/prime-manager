package br.com.hadryan.manager.mapper.response;

import br.com.hadryan.manager.model.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {

    private Long id;

    private String barcode;

    private String name;

    private String brand;

    private String description;

    private Double buyPrice;

    private Double sellPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer stockQuantity;

    private ProductCategory category;

}
