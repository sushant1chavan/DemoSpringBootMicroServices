package com.sushant.microservices.product.controller;

import com.sushant.microservices.product.dto.ProductRequest;
import com.sushant.microservices.product.dto.ProductResponse;
import com.sushant.microservices.product.model.Product;
import com.sushant.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
//remember that , we can use @RequiredArgsConstructor instead of @Autowired and
//adding that in constructor manually, also vimp here that we have to mark this field as final, then only it will work
//as below private final ProductService productService
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
