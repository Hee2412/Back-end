package com.example.heecoffee.Service.impl;

import com.example.heecoffee.Dto.Request.ProductRequest;
import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Response.ProductResponse;
import com.example.heecoffee.Dto.Response.ProductFilter;
import com.example.heecoffee.Dto.Response.TypeResponse;
import com.example.heecoffee.Exception.ErrorCodeConstant;
import com.example.heecoffee.Exception.NotFoundException;
import com.example.heecoffee.Model.Product;
import com.example.heecoffee.Model.Type;
import com.example.heecoffee.Repository.ProductRepository;
import com.example.heecoffee.Repository.Specification.ProductSpecification;
import com.example.heecoffee.Repository.TypeRepository;
import com.example.heecoffee.Service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;

    public ProductServiceImpl(ProductRepository productRepository, TypeRepository typeRepository) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
    }

    //1. For mapping product out UI
    private TypeResponse mapToTypeResponse(Type type) {
        return new TypeResponse(type.getId(), type.getTypes());
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(p -> new ProductResponse(
                p.getId(),
                p.getProductName(),
                p.getDescription(),
                p.getPrice(),
                p.getImg(),
                p.getTypes().stream().map(this::mapToTypeResponse).toList()
        )).toList();
    }

    //2. Filter
    @Override
    public ResponseEntity<ApiResponse> filter(ProductFilter productFilter) {
        Specification<Product> specification =
                ProductSpecification.name(productFilter.getProductName())
                        .and(ProductSpecification.type(productFilter.getTypeName()))
                        .and(ProductSpecification.typeId(productFilter.getTypeId()));
        Pageable pageable = PageRequest.of(productFilter.getPageNumber(), productFilter.getPageSize());
        Page<Product> product = productRepository.findAll(specification, pageable);

        List<ProductResponse> dto = product.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getProductName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getImg(),
                        p.getTypes().stream().map(this::mapToTypeResponse).toList()
                )).toList();
        Page<ProductResponse> productDto = new PageImpl<>(dto, pageable, product.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", productDto));
    }

    //3. product details
    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found", ErrorCodeConstant.PRODUCT_NOT_FOUND));

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getImg(),
                product.getTypes().stream().map(this::mapToTypeResponse).toList()
        );
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImg(request.getImg());

        if (request.getTypeIds() != null && !request.getTypeIds().isEmpty()) {
            Set<Type> types = new HashSet<>();
            for (Integer typeId : request.getTypeIds()) {
                Type type = typeRepository.findById(typeId)
                        .orElseThrow(() -> new NotFoundException("Type not found with id: " + typeId, ErrorCodeConstant.TYPE_NOT_FOUND));
                type.getProducts().add(product);

                types.add(type);
            }
            product.setTypes(types);
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Integer productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId, ErrorCodeConstant.PRODUCT_NOT_FOUND));

        // Update basic fields
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // Only required when have new source
        String newImgUrl = request.getImg();
        if (newImgUrl != null && !newImgUrl.trim().isEmpty()) {
            product.setImg(newImgUrl);
        }

        // ✅ Update types - Clear old types and set new ones
        if (request.getTypeIds() != null) {
            Set<Type> types = new HashSet<>();
            for (Integer typeId : request.getTypeIds()) {
                Type type = typeRepository.findById(typeId)
                        .orElseThrow(() -> new NotFoundException("Type not found with id: " + typeId, ErrorCodeConstant.TYPE_NOT_FOUND));
                types.add(type);
            }
            product.setTypes(types); // This will clear old types and set new ones
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId, ErrorCodeConstant.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }
}
