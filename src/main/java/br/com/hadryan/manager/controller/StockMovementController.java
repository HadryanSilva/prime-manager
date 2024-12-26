package br.com.hadryan.manager.controller;

import br.com.hadryan.manager.mapper.request.StockMovementRequest;
import br.com.hadryan.manager.mapper.response.StockMovementResponse;
import br.com.hadryan.manager.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<List<StockMovementResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(stockMovementService.findAll(page, size));

    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(stockMovementService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> save(@RequestBody StockMovementRequest request) {
        var response = stockMovementService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/stock/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stockMovementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
