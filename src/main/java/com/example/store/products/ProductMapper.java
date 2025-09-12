package com.example.store.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);

    @Mapping(target = "id",  ignore = true)
    void update(ProductDto request, @MappingTarget Product product);

}
