package com.example.heecoffee.Service;

import com.example.heecoffee.Dto.Request.ProductRequest;
import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Response.ProductResponse;
import com.example.heecoffee.Dto.Response.ProductFilter;
import com.example.heecoffee.Model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();

    ResponseEntity<ApiResponse> filter(ProductFilter productFilter);

    ProductResponse getProductById(Integer id);

    Product createProduct(ProductRequest request);

    Product updateProduct(Integer productId, ProductRequest request);

    void deleteProduct(Integer productId);
}
