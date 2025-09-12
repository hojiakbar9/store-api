package com.example.store.products;

import com.example.store.orders.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private OrderItemRepository orderItemRepository;

    public List<ProductDto> getAllProducts(Byte categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.toDto(product);
    }
    public ProductDto createNewProduct(ProductDto request) {
        Product product = productMapper.toEntity(request);
        Byte categoryId = request.getCategoryId();

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        product.setCategory(category);
        productRepository.save(product);
        ProductDto dto = productMapper.toDto(product);
        dto.setId(product.getId());
        dto.setCategoryId(categoryId);

        return dto;
    }
    public ProductDto updateProduct(ProductDto request, Long id ) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        productMapper.update(request, product);
        product.setCategory(category);

        productRepository.save(product);

        request.setId(product.getId());

        return request;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        if (!orderItemRepository.findByProduct(product).isEmpty()) {
            throw new ProductInOrderItemException();
        }
        productRepository.delete(product);
    }

}
