package br.com.hadryan.manager.mapper.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;

    private String name;

    private String brand;

    private String description;

    private Double buyPrice;

    private Double sellPrice;

}
