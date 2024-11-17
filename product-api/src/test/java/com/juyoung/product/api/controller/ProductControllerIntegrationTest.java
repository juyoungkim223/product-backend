package com.juyoung.product.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local") // 테스트 프로파일 활성화
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCheapestByCategory() throws Exception {
        mockMvc.perform(get("/api/products/cheapest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value("34,100"));
    }

    @Test
    void testGetBrandTotalPrice() throws Exception {

        mockMvc.perform(get("/api/products/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("D"))
                .andExpect(jsonPath("$.products", hasSize(8)))
                .andExpect(jsonPath("$.totalPrice", is("36,100"))); // Sum of prices
    }

    @Test
    void testGetCategoryPriceRange() throws Exception {

        mockMvc.perform(get("/api/products/category/상의"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("상의"))
                .andExpect(jsonPath("$.cheapest.brand").value("C"))
                .andExpect(jsonPath("$.cheapest.price").value("10,000"))
                .andExpect(jsonPath("$.mostExpensive.brand").value("I"))
                .andExpect(jsonPath("$.mostExpensive.price").value("11,400"));
    }

    @Test
    void testAddProduct() throws Exception {
        String request = """
                {
                  "categoryId": 1,
                  "brandId": 10,
                  "price": 200
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully!"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        String request = """
                {
                  "productId": 1,
                  "categoryId": 1,
                  "brandId": 1,
                  "price": 300
                }
                """;

        mockMvc.perform(put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().string("Product updated successfully!"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        String request = """
                {
                  "productId": 1
                }
                """;
        mockMvc.perform(delete("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully!"));
    }

}
