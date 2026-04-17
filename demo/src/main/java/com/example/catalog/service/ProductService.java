package com.example.catalog.service;

import com.example.catalog.dto.ProductRequest;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.entity.Producer;
import com.example.catalog.entity.Product;
import com.example.catalog.exception.ResourceNotFoundException;
import com.example.catalog.repository.ProducerRepository;
import com.example.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Producer producer = producerRepository.findById(request.getProducerId())
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found with id: " + request.getProducerId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .producer(producer)
                .attributes(request.getAttributes())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Producer producer = producerRepository.findById(request.getProducerId())
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found with id: " + request.getProducerId()));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setProducer(producer);
        product.setAttributes(request.getAttributes());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .producerName(product.getProducer().getName())
                .attributes(product.getAttributes())
                .build();
    }
}