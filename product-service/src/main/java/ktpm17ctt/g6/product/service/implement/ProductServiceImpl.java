package ktpm17ctt.g6.product.service.implement;

import ktpm17ctt.g6.product.dto.request.ProductRequest;
import ktpm17ctt.g6.product.dto.response.ProductResponse;
import ktpm17ctt.g6.product.entity.Category;
import ktpm17ctt.g6.product.entity.Product;
import ktpm17ctt.g6.product.exception.NotFoundException;
import ktpm17ctt.g6.product.mapper.CategoryMapper;
import ktpm17ctt.g6.product.mapper.ProductMapper;
import ktpm17ctt.g6.product.repository.CategoryRepository;
import ktpm17ctt.g6.product.repository.ProductRepository;
import ktpm17ctt.g6.product.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;
    CategoryMapper categoryMapper;

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category ID", productRequest.getCategoryId()));
        Product product = productMapper.toProduct(productRequest);
        product.setCategory(category);
        product.setCode(generateCode());
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse update(String id, ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category ID", productRequest.getCategoryId()));
        Product db = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product ID", id));
        Product product;
        product = productMapper.toProduct(productRequest);
        product.setCategory(category);
        product.setId(id);
        product.setCode(db.getCode());
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductResponse> findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product ID", id));
        return Optional.of(productMapper.toProductResponse(product));
    }

    @Override
    public List<ProductResponse> findAll() {
        var products = productRepository.findAll();
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    @Override
    public List<ProductResponse> search(String keyword) {
        var products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder("SP");

        // Lấy ngày giờ hiện tại theo định dạng yyyyMMddHHmm
        String dateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        code.append(dateTime);

        // Đếm số sản phẩm trong DB
        int count = Math.toIntExact(productRepository.count());

        // Thêm số thứ tự 4 chữ số (zero padding)
        code.append(String.format("%04d", count + 1));

        return code.toString();
    }

}
