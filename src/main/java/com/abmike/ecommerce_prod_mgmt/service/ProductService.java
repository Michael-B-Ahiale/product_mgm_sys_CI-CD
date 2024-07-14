package com.abmike.ecommerce_prod_mgmt.service;

import com.abmike.ecommerce_prod_mgmt.model.Product;
import com.abmike.ecommerce_prod_mgmt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }

    public Page<Product> searchProducts(String query, Pageable pageable) {
        return productRepository.searchProducts(query, pageable);
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public Optional<Product> partialUpdateProduct(Long id, Product partialProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (partialProduct.getName() != null) {
                        existingProduct.setName(partialProduct.getName());
                    }
                    if (partialProduct.getDescription() != null) {
                        existingProduct.setDescription(partialProduct.getDescription());
                    }
                    if (partialProduct.getPrice() != null) {
                        existingProduct.setPrice(partialProduct.getPrice());
                    }
                    if (partialProduct.getCategory() != null) {
                        existingProduct.setCategory(partialProduct.getCategory());
                    }
                    return productRepository.save(existingProduct);
                });
    }

    public List<Product> getProductsInPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findProductsInPriceRange(minPrice, maxPrice);
    }

    public Map<String, Long> getProductCountByCategory() {
        return productRepository.countProductsByCategory().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }
}