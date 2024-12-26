package br.com.hadryan.manager.model.enums;

import lombok.Getter;

@Getter
public enum StockMoveType {

    IN("In"),
    OUT("Out");

    private final String description;

    StockMoveType(String description) {
        this.description = description;
    }

}
