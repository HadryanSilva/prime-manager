package br.com.hadryan.manager.mapper.request;

import br.com.hadryan.manager.model.enums.StockMoveType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMovementRequest {

    private Long productId;

    private Integer quantity;

    private StockMoveType type;

}
