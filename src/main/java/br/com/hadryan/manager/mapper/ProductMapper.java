package br.com.hadryan.manager.mapper;

import br.com.hadryan.manager.mapper.request.ProductRequest;
import br.com.hadryan.manager.mapper.response.ProductResponse;
import br.com.hadryan.manager.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product postToProduct(ProductRequest productRequest);

    ProductResponse productToPost(Product product);

}
