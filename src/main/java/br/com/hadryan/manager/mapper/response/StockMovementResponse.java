package br.com.hadryan.manager.mapper.response;

import br.com.hadryan.manager.model.enums.StockMoveType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StockMovementResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Integer quantity;

    private StockMoveType type;

    private LocalDateTime createdAt;

}
