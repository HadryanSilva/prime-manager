package br.com.hadryan.manager.service;

import br.com.hadryan.manager.exception.NotFoundException;
import br.com.hadryan.manager.exception.StockNotAvailableException;
import br.com.hadryan.manager.mapper.StockMovementMapper;
import br.com.hadryan.manager.mapper.request.StockMovementRequest;
import br.com.hadryan.manager.mapper.response.StockMovementResponse;
import br.com.hadryan.manager.model.Product;
import br.com.hadryan.manager.model.StockMovement;
import br.com.hadryan.manager.model.enums.StockMoveType;
import br.com.hadryan.manager.repository.ProductRepository;
import br.com.hadryan.manager.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final StockMovementMapper stockMovementMapper;

    public List<StockMovementResponse> findAll(int page, int size) {
        log.info("Finding all stock movements");
        return stockMovementRepository.findAll(PageRequest.of(page, size))
                .map(stockMovementMapper::stockMovementToResponse)
                .getContent();
    }

    public StockMovementResponse findById(Long id) {
        log.info("Finding stock movement by id: {}", id);
        return stockMovementRepository.findById(id)
                .map(stockMovementMapper::stockMovementToResponse)
                .orElseThrow(() -> new NotFoundException("Stock movement not found"));
    }

    @Transactional
    public StockMovementResponse save(StockMovementRequest request) {
        log.info("Saving stock movement: {}", request);

        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        var stockMovement = stockMovementMapper.postToStockMovement(request);
        stockMovement.setProduct(product);
        stockMovement.setCreatedAt(LocalDateTime.now());

        if (!isStockQuantityAvailable(product, stockMovement)) {
            throw new StockNotAvailableException("Stock quantity not available for this product");
        }

        var stockMovementSaved = stockMovementRepository.save(stockMovement);
        updateStockQuantity(product, stockMovement);
        return stockMovementMapper.stockMovementToResponse(stockMovementSaved);
    }


    @Transactional
    public void delete(Long id) {
        var stockMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stock movement not found"));
        log.info("Deleting stock movement by id: {}", id);
        stockMovementRepository.delete(stockMovement);
        rollbackStockQuantity(stockMovement.getProduct(), stockMovement);
    }

    private void updateStockQuantity(Product product, StockMovement stockMovement) {
        if (stockMovement.getType().equals(StockMoveType.OUT)) {
            log.info("Move stock is OUT, decreasing stock quantity");
            product.setStockQuantity(product.getStockQuantity() - stockMovement.getQuantity());
        } else {
            log.info("Move stock is IN, increasing stock quantity");
            product.setStockQuantity(product.getStockQuantity() + stockMovement.getQuantity());
        }
        productRepository.save(product);
    }

    private void rollbackStockQuantity(Product product, StockMovement stockMovement) {
        if (stockMovement.getType().equals(StockMoveType.OUT)) {
            log.info("Rollback stock quantity, increasing stock quantity");
            product.setStockQuantity(product.getStockQuantity() + stockMovement.getQuantity());
        } else {
            log.info("Rollback stock quantity, decreasing stock quantity");
            product.setStockQuantity(product.getStockQuantity() - stockMovement.getQuantity());
        }
        productRepository.save(product);
    }

    private boolean isStockQuantityAvailable(Product product, StockMovement stockMovement) {
        if (stockMovement.getType().equals(StockMoveType.OUT)) {
            return product.getStockQuantity() >= stockMovement.getQuantity();
        }
        return true;
    }

}
