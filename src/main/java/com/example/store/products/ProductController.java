package com.example.store.products;

import com.example.store.common.ErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;



    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId
    ) {
        return productService.getAllProducts(categoryId);
    }
    @PostMapping
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody ProductDto request, UriComponentsBuilder builder) {
       var productDto =  productService.createNewProduct(request);
        URI uri = builder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto request, @PathVariable Long id ) {
        return productService.updateProduct(request, id);
    }
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCategoryNotFoundException() {
        return ResponseEntity.badRequest().body(new ErrorDto("Category could not be found."));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException() {
        return ResponseEntity.badRequest().body(new ErrorDto("Product could not be found."));
    }
    @ExceptionHandler(ProductInOrderItemException.class)
    public ResponseEntity<ErrorDto> handleProductInOrderItemException() {
        return ResponseEntity.badRequest().body(new ErrorDto("Product is in one or more orders."));
    }
}
