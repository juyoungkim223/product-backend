package com.juyoung.product.api.service;

import com.juyoung.product.api.entity.Brand;
import com.juyoung.product.api.entity.Category;
import com.juyoung.product.api.entity.Product;
import com.juyoung.product.api.repository.BrandRepository;
import com.juyoung.product.api.repository.CategoryRepository;
import com.juyoung.product.api.repository.ProductRepository;
import com.juyoung.product.data.dto.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("API 1")
    void testGetCheapestByCategory() {
        // Given
        Category category1 = new Category(1L, "상의");
        Category category2 = new Category(2L, "아우터");
        List<Product> products = Arrays.asList(
                new Product(1L, category1, new Brand(1L, "A"), 100),
                new Product(2L, category1, new Brand(2L, "B"), 1000),
                new Product(3L, category2, new Brand(1L, "A"), 200)
        );
        // findAll() returns
        when(productRepository.findAll()).thenReturn(products);

        // Act
        CheapestProductResponseDTO result = productService.getCheapestByCategory();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getProducts().size());
        assertEquals("300", result.getTotalAmount());
    }

    @Test
    @DisplayName("API 2")
    void testGetCheapestBrandWithTotalPrice() {
        // Given
        Brand brand1 = new Brand(1L, "A");
        Brand brand2 = new Brand(2L, "B");
        Category category = new Category(1L, "상의");
        Product product1 = new Product(1L, category, brand1, 100);
        Product product2 = new Product(2L, category, brand1, 200);
        Product product3 = new Product(1L, category, brand2, 300);
        Product product4 = new Product(2L, category, brand2, 400);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3, product4));

        // Act
        BrandWithTotalPriceResponseDTO result = productService.getCheapestBrandWithTotalPrice();

        // Assert
        assertNotNull(result);
        assertEquals("A", result.getBrand());
        assertEquals("300", result.getTotalPrice());
    }

    @Test
    @DisplayName("API 3")
    void testGetCategoryPriceRange() {
        // Given
        Category category = new Category(1L, "상의");
        Brand brand = new Brand(1L, "A");
        Product cheapestProduct = new Product(1L, category, brand, 100);
        Product secondCheapestProduct = new Product(2L, category, brand, 200);
        Product mostExpensiveProduct = new Product(3L, category, brand, 300);

        when(productRepository.findByCategoryName("상의")).thenReturn(Arrays.asList(cheapestProduct, secondCheapestProduct, mostExpensiveProduct));

        // Act
        CategoryPriceRangeResponseDTO result = productService.getCategoryPriceRange("상의");

        // Assert
        assertNotNull(result);
        assertEquals("상의", result.getCategory());
        assertEquals("100", result.getCheapest().getPrice());
        assertEquals("300", result.getMostExpensive().getPrice());
    }

    @Test
    @DisplayName("API 4")
    void testAddProduct() {
        // Given
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(1L, 1L, 200);
        Category category = new Category(1L, "Shoes");
        Brand brand = new Brand(1L, "Nike");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(productRepository.existsByCategoryAndBrand(category, brand)).thenReturn(false);

        // Act
        productService.addProduct(productRequestDTO);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("API 4")
    void testUpdateProduct() {
        // Given
        ProductUpdateRequestDTO updateRequestDTO = new ProductUpdateRequestDTO(1L, 1L, 1L, 300);
        Category category = new Category(1L, "상의");
        Brand brand = new Brand(1L, "A");
        Product existingProduct = new Product(1L, category, brand, 200);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        // Act
        productService.updateProduct(updateRequestDTO);

        // Assert
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals(300, existingProduct.getPrice());
    }

    @Test
    @DisplayName("API 4")
    void testDeleteProduct() {
        // Act
        productService.deleteProduct(new ProductDeletionRequestDTO(1L));

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }
}
