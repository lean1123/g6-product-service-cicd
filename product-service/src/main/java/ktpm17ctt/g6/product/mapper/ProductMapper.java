package ktpm17ctt.g6.product.mapper;

import ktpm17ctt.g6.product.dto.request.ProductRequest;
import ktpm17ctt.g6.product.dto.response.ProductResponse;
import ktpm17ctt.g6.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "type", source = "type")
    Product toProduct(ProductRequest productRequest);
    ProductResponse toProductResponse(Product product);
}
