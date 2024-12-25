package br.com.hadryan.manager.service;

import br.com.hadryan.manager.exception.NotFoundException;
import br.com.hadryan.manager.mapper.ProductMapper;
import br.com.hadryan.manager.mapper.request.ProductRequest;
import br.com.hadryan.manager.mapper.response.ProductResponse;
import br.com.hadryan.manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(int page, int size) {
        log.info("Finding all products");
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::productToPost)
                .stream()
                .toList();
    }

    public ProductResponse findById(Long id) {
        log.info("Finding product: {}", id);
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        log.info("Product found: {}", product.getName());
        return productMapper.productToPost(product);
    }

    public ProductResponse save(ProductRequest request) {
        log.info("Saving product: {}", request.getName());
        var productToSave = productMapper.postToProduct(request);
        productToSave.setCreatedAt(LocalDateTime.now());
        var savedProduct = productRepository.save(productToSave);
        return productMapper.productToPost(savedProduct);
    }

    @Transactional
    public void update(Long id, ProductRequest request) {
        log.info("Updating product: {}", id);
        var productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Optional.ofNullable(request.getName()).ifPresent(productToUpdate::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(productToUpdate::setDescription);
        Optional.ofNullable(request.getBuyPrice()).ifPresent(productToUpdate::setBuyPrice);
        Optional.ofNullable(request.getSellPrice()).ifPresent(productToUpdate::setSellPrice);

        productToUpdate.setUpdatedAt(LocalDateTime.now());
        productRepository.save(productToUpdate);
        log.info("Product updated successfully: {}", productToUpdate.getName());
    }

    public void delete(Long id) {
        log.info("Deleting product: {}", id);
        var productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(productToDelete);
        log.info("Product deleted successfully: {}", productToDelete.getName());
    }

}
