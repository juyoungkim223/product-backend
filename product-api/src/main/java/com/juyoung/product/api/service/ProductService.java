package com.juyoung.product.api.service;
import com.juyoung.product.api.entity.Brand;
import com.juyoung.product.api.entity.Category;
import com.juyoung.product.api.entity.Product;
import com.juyoung.product.api.repository.BrandRepository;
import com.juyoung.product.api.repository.CategoryRepository;
import com.juyoung.product.api.repository.ProductRepository;
import com.juyoung.product.data.dto.api.*;
import com.juyoung.product.data.exception.ApiException;
import com.juyoung.product.data.util.PriceFormatterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public CheapestProductResponseDTO getCheapestByCategory() {
        List<ProductResponseDTO> products = productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.minBy(Comparator.comparingInt(Product::getPrice))))
                .values().stream()
                .map(optional -> optional.orElseThrow(() -> new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND)))
                .map(product -> new ProductResponseDTO(product.getCategory().getName(),
                        product.getBrand().getName(),
                        PriceFormatterUtil.formatPrice(product.getPrice())))
                .collect(Collectors.toList());
        int totalAmount = products.stream().mapToInt(product -> PriceFormatterUtil.parsePriceToInt(product.getPrice())).sum();
        return new CheapestProductResponseDTO(products, totalAmount);
    }

    // 2번 API: 단일 브랜드로 모든 카테고리 상품 구매 시 최저가격 조회
    public BrandWithTotalPriceResponseDTO getCheapestBrandWithTotalPrice() {
        // 상품 얻기 group by 브랜드명
        Map<String, List<Product>> productsByBrand = productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(product -> product.getBrand().getName()));

        // 브랜드별 총액 계산 and 총액이 가장 작은 브랜드 찾기
        return productsByBrand.entrySet()
                .stream()
                .map(entry -> {
                    String brand = entry.getKey();
                    List<Product> products = entry.getValue();

                    int totalPrice = products.stream()
                            .mapToInt(Product::getPrice)
                            .sum();

                    // DTO 변환
                    List<ProductResponseDTO> productResponses = products.stream()
                            .map(product -> new ProductResponseDTO(
                                    product.getCategory().getName(),
                                    null,
                                    PriceFormatterUtil.formatPrice(product.getPrice())))
                            .collect(Collectors.toList());

                    return new BrandWithTotalPriceResponseDTO(brand, productResponses, totalPrice);
                })
                .min(Comparator.comparingInt(DTO -> PriceFormatterUtil.parsePriceToInt(DTO.getTotalPrice()))) // 총액이 가장 작은 브랜드 찾기
                .orElseThrow(() -> new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
    // 3번 API: 카테고리 이름으로 최저, 최고 가격 조회
    public CategoryPriceRangeResponseDTO getCategoryPriceRange(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);

        if (products.isEmpty()) {
            throw new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Product cheapest = products.stream()
                .min(Comparator.comparingInt(Product::getPrice))
                .orElseThrow(() -> new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

        Product mostExpensive = products.stream()
                .max(Comparator.comparingInt(Product::getPrice))
                .orElseThrow(() -> new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

        return new CategoryPriceRangeResponseDTO(
                categoryName,
                new ProductResponseDTO(null, cheapest.getBrand().getName(), PriceFormatterUtil.formatPrice(cheapest.getPrice())),
                new ProductResponseDTO(null, mostExpensive.getBrand().getName(), PriceFormatterUtil.formatPrice(mostExpensive.getPrice()))
        );
    }

    public void addProduct(ProductRequestDTO productRequestDTO) {
        // 유효성
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new ApiException(ResponseMeta.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));

        // 유효성
        Brand brand = brandRepository.findById(productRequestDTO.getBrandId())
                .orElseThrow(() -> new ApiException(ResponseMeta.BRAND_NOT_FOUND, HttpStatus.NOT_FOUND));

        // 상품 추가 제약조건 유효성
        boolean exists = productRepository.existsByCategoryAndBrand(category, brand);
        if (exists) {
            throw new ApiException(ResponseMeta.PRODUCT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        // 상품 생성
        Product product = new Product();
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(productRequestDTO.getPrice());

        productRepository.save(product);
    }

    public void updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO) {
        // 상품 유효성
        Product product = productRepository.findById(productUpdateRequestDTO.getProductId())
                .orElseThrow(() -> new ApiException(ResponseMeta.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

        // 카테고리 유효성
        Category category = categoryRepository.findById(productUpdateRequestDTO.getCategoryId())
                .orElseThrow(() -> new ApiException(ResponseMeta.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));

        // 브랜드 유효성
        Brand brand = brandRepository.findById(productUpdateRequestDTO.getBrandId())
                .orElseThrow(() -> new ApiException(ResponseMeta.BRAND_NOT_FOUND, HttpStatus.NOT_FOUND));

        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(productUpdateRequestDTO.getPrice());

        productRepository.save(product);
    }

    public void deleteProduct(ProductDeletionRequestDTO requestDTO) {
        productRepository.deleteById(requestDTO.getProductId());
    }
}

