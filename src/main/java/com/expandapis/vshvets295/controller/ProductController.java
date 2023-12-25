package com.expandapis.vshvets295.controller;

import com.expandapis.vshvets295.dto.ProductRecordsList;
import com.expandapis.vshvets295.dto.ProductDto;
import com.expandapis.vshvets295.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewProducts(@RequestBody ProductRecordsList list) {
        return productService.addNewProducts(list);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
