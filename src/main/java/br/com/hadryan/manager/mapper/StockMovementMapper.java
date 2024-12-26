package br.com.hadryan.manager.mapper;

import br.com.hadryan.manager.mapper.request.StockMovementRequest;
import br.com.hadryan.manager.mapper.response.StockMovementResponse;
import br.com.hadryan.manager.model.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {

    StockMovement postToStockMovement(StockMovementRequest stockMovementRequest);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    StockMovementResponse stockMovementToResponse(StockMovement stockMovement);

}
