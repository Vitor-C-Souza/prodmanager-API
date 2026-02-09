package teste.autoflex.vitorcsouza.prodmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindAllProducts() throws Exception {
        productRepository.save(Product.builder().name("Desk").price(BigDecimal.valueOf(300)).build());
        productRepository.save(Product.builder().name("Desk").price(BigDecimal.valueOf(300)).build());

        mockMvc.perform(get("/api/v1/products")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindProductById() throws Exception {
        Product product = productRepository.save(Product.builder().name("Monitor").price(BigDecimal.valueOf(900)).build());

        mockMvc.perform(get("/api/v1/products/" + product.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturn404WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteProduct() throws Exception {
        Product product = productRepository.save(Product.builder().name("Keyboard").price(BigDecimal.valueOf(80)).build());

        mockMvc.perform(delete("/api/v1/products/" + product.getId())).andExpect(status().isNoContent());

        assertTrue(productRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturn404WhenDeletingNonExistingProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDissociateMaterialWithoutDeletingIt() throws Exception {
        RawMaterial material = rawMaterialRepository.save(
                RawMaterial.builder()
                        .name("Steel")
                        .code("ST-01")
                        .stockQuantity(100)
                        .build()
        );

        Product product = Product.builder()
                .name("Robot")
                .code("RB-01")
                .price(new BigDecimal("500"))
                .materials(new ArrayList<>())
                .build();

        ProductRawMaterial relationship = new ProductRawMaterial();
        relationship.setProduct(product);
        relationship.setRawMaterial(material);
        relationship.setRequiredQuantity(5);

        product.getMaterials().add(relationship);

        product = productRepository.saveAndFlush(product);

        UUID productId = product.getId();
        UUID relationshipId = product.getMaterials().get(0).getId();

        mockMvc.perform(delete("/api/v1/products/" + productId + "/materials/" + relationshipId))
                .andExpect(status().isNoContent());

        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        assertTrue(updatedProduct.getMaterials().isEmpty(), "Relationship should be gone from Product");

        assertTrue(rawMaterialRepository.existsById(material.getId()), "RawMaterial must still exist");
    }
}