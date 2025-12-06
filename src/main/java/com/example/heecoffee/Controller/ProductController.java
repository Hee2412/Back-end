package com.example.heecoffee.Controller;

import com.example.heecoffee.Dto.Request.ProductRequest;
import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Response.ProductResponse;
import com.example.heecoffee.Dto.Response.ProductFilter;
import com.example.heecoffee.Model.Product;
import com.example.heecoffee.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //1. Get all product for mapping it out UI
    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts() {
        List<ProductResponse> products = productService.getProducts();
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    //2. Get by id for product details
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Integer id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Success", product));
    }

    //3. Filter
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> filter(ProductFilter productFilter) {
        return productService.filter(productFilter);
    }

    // ProductController.java - Update methods
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(new ApiResponse("Success", product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductRequest request
    ) {
        Product product = productService.updateProduct(productId, request);
        return ResponseEntity.ok(new ApiResponse("Success", product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Success", null));
    }
}
