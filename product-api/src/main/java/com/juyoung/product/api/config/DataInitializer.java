package com.juyoung.product.api.config;

import com.juyoung.product.api.entity.Brand;
import com.juyoung.product.api.entity.Category;
import com.juyoung.product.api.entity.Product;
import com.juyoung.product.api.repository.BrandRepository;
import com.juyoung.product.api.repository.CategoryRepository;
import com.juyoung.product.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Profile("local") // local 프로파일에서만 실행
public class DataInitializer implements CommandLineRunner {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // 브랜드 데이터 삽입
        Brand[] brands = {
                new Brand(null, "A"),
                new Brand(null, "B"),
                new Brand(null, "C"),
                new Brand(null, "D"),
                new Brand(null, "E"),
                new Brand(null, "F"),
                new Brand(null, "G"),
                new Brand(null, "H"),
                new Brand(null, "I")
        };
        brandRepository.saveAll(Arrays.asList(brands));

        // 카테고리 데이터 삽입
        Category[] categories = {
                new Category(null, "상의"),
                new Category(null, "아우터"),
                new Category(null, "바지"),
                new Category(null, "스니커즈"),
                new Category(null, "가방"),
                new Category(null, "모자"),
                new Category(null, "양말"),
                new Category(null, "액세서리")
        };
        categoryRepository.saveAll(Arrays.asList(categories));

        // 상품 데이터 삽입
        Product[] products = {
                // 상의
                new Product(null, categories[0], brands[0], 11200),
                new Product(null, categories[0], brands[1], 10500),
                new Product(null, categories[0], brands[2], 10000),
                new Product(null, categories[0], brands[3], 10100),
                new Product(null, categories[0], brands[4], 10700),
                new Product(null, categories[0], brands[5], 11200),
                new Product(null, categories[0], brands[6], 10500),
                new Product(null, categories[0], brands[7], 10800),
                new Product(null, categories[0], brands[8], 11400),

                // 아우터
                new Product(null, categories[1], brands[0], 5500),
                new Product(null, categories[1], brands[1], 5900),
                new Product(null, categories[1], brands[2], 6200),
                new Product(null, categories[1], brands[3], 5100),
                new Product(null, categories[1], brands[4], 5000),
                new Product(null, categories[1], brands[5], 7200),
                new Product(null, categories[1], brands[6], 5800),
                new Product(null, categories[1], brands[7], 6300),
                new Product(null, categories[1], brands[8], 6700),

                // 바지
                new Product(null, categories[2], brands[0], 4200),
                new Product(null, categories[2], brands[1], 3800),
                new Product(null, categories[2], brands[2], 3300),
                new Product(null, categories[2], brands[3], 3000),
                new Product(null, categories[2], brands[4], 3800),
                new Product(null, categories[2], brands[5], 4000),
                new Product(null, categories[2], brands[6], 3900),
                new Product(null, categories[2], brands[7], 3100),
                new Product(null, categories[2], brands[8], 3200),

                // 스니커즈
                new Product(null, categories[3], brands[0], 9000),
                new Product(null, categories[3], brands[1], 9100),
                new Product(null, categories[3], brands[2], 9200),
                new Product(null, categories[3], brands[3], 9500),
                new Product(null, categories[3], brands[4], 9900),
                new Product(null, categories[3], brands[5], 9300),
                new Product(null, categories[3], brands[6], 9000),
                new Product(null, categories[3], brands[7], 9700),
                new Product(null, categories[3], brands[8], 9500),

                // 가방
                new Product(null, categories[4], brands[0], 2000),
                new Product(null, categories[4], brands[1], 2100),
                new Product(null, categories[4], brands[2], 2200),
                new Product(null, categories[4], brands[3], 2500),
                new Product(null, categories[4], brands[4], 2300),
                new Product(null, categories[4], brands[5], 2100),
                new Product(null, categories[4], brands[6], 2200),
                new Product(null, categories[4], brands[7], 2100),
                new Product(null, categories[4], brands[8], 2400),

                // 모자
                new Product(null, categories[5], brands[0], 1700),
                new Product(null, categories[5], brands[1], 2000),
                new Product(null, categories[5], brands[2], 1900),
                new Product(null, categories[5], brands[3], 1500),
                new Product(null, categories[5], brands[4], 1800),
                new Product(null, categories[5], brands[5], 1600),
                new Product(null, categories[5], brands[6], 1700),
                new Product(null, categories[5], brands[7], 1600),
                new Product(null, categories[5], brands[8], 1700),

                // 양말
                new Product(null, categories[6], brands[0], 1800),
                new Product(null, categories[6], brands[1], 2000),
                new Product(null, categories[6], brands[2], 2200),
                new Product(null, categories[6], brands[3], 2400),
                new Product(null, categories[6], brands[4], 2100),
                new Product(null, categories[6], brands[5], 2300),
                new Product(null, categories[6], brands[6], 2100),
                new Product(null, categories[6], brands[7], 2000),
                new Product(null, categories[6], brands[8], 1700),

                // 액세서리
                new Product(null, categories[7], brands[0], 2300),
                new Product(null, categories[7], brands[1], 2200),
                new Product(null, categories[7], brands[2], 2100),
                new Product(null, categories[7], brands[3], 2000),
                new Product(null, categories[7], brands[4], 2100),
                new Product(null, categories[7], brands[5], 1900),
                new Product(null, categories[7], brands[6], 2000),
                new Product(null, categories[7], brands[7], 2000),
                new Product(null, categories[7], brands[8], 2400)
        };

        productRepository.saveAll(Arrays.asList(products));
    }
}
