package com.juyoung.product.api.repository;
import com.juyoung.product.api.entity.Brand;
import com.juyoung.product.api.entity.Category;
import com.juyoung.product.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByBrandName(String brandName);
    Optional<Product> findByCategoryNameAndBrandName(String categoryName, String brandName);
    Optional<Product> findByCategoryAndBrand(Category category, Brand brand);
}

