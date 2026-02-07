package teste.autoflex.vitorcsouza.prodmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        String body = """
                    {                
                      "name": "Chair",
                      "code": "CHR-01",
                      "price": 120.50
                    }
                """;

        mockMvc.perform(post("/api/v1/products").contentType("application/json").content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Chair")).andExpect(jsonPath("$.price").value(120.50));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidBody() throws Exception {
        String body = """
                    {
                      "name": "",
                      "price": -10
                    }
                """;

        mockMvc.perform(post("/api/v1/products").contentType("application/json").content(body)).andExpect(status().isBadRequest());
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        productRepository.save(Product.builder().name("Desk").price(BigDecimal.valueOf(300)).build());
        productRepository.save(Product.builder().name("Desk").price(BigDecimal.valueOf(300)).build());

        mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldFindProductById() throws Exception {
        Product product = productRepository.save(Product.builder().name("Monitor").price(BigDecimal.valueOf(900)).build());

        mockMvc.perform(get("/api/v1/products/" + product.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        Product product = productRepository.save(Product.builder().name("Mouse").price(BigDecimal.valueOf(50)).build());

        String body = """
                    {
                      "name": "Gaming Mouse",
                      "code": "GST-01",
                      "price": 150
                    }
                """;

        mockMvc.perform(put("/api/v1/products/" + product.getId()).contentType("application/json").content(body)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Gaming Mouse")).andExpect(jsonPath("$.price").value(150));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingProduct() throws Exception {
        String body = """
                    {
                      "name": "Ghost",
                      "code": "CHR-01",
                      "price": 100
                    }
                """;

        mockMvc.perform(put("/api/v1/products/" + UUID.randomUUID()).contentType("application/json").content(body)).andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Product product = productRepository.save(Product.builder().name("Keyboard").price(BigDecimal.valueOf(80)).build());

        mockMvc.perform(delete("/api/v1/products/" + product.getId())).andExpect(status().isNoContent());

        assertTrue(productRepository.findAll().isEmpty());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }
}