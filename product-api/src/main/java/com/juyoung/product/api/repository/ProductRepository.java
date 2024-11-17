package com.juyoung.product.api.repository;
import com.juyoung.product.api.entity.Brand;
import com.juyoung.product.api.entity.Category;
import com.juyoung.product.api.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"brand", "category"})
    @NonNull
    List<Product> findAll();
    @EntityGraph(attributePaths = {"brand", "category"})
    List<Product> findByCategoryName(String categoryName);
    boolean existsByCategoryAndBrand(Category category, Brand brand);

}

