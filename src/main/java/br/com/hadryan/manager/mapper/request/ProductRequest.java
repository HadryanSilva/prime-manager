package br.com.hadryan.manager.mapper.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String name;

    private String brand;

    private String description;

    private Double buyPrice;

    private Double sellPrice;

}
