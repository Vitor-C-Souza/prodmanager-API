package teste.autoflex.vitorcsouza.prodmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductRawMaterialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository productRawMaterialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private UUID rawMaterialId;

    @BeforeEach
    void setup() {
        productRawMaterialRepository.deleteAll();
        productRepository.deleteAll();
        rawMaterialRepository.deleteAll();

        RawMaterial rm = RawMaterial.builder()
                .name("Steel")
                .stockQuantity(100)
                .build();

        rawMaterialRepository.save(rm);
        rawMaterialId = rm.getId();

        Product product = Product.builder()
                .name("Table")
                .price(BigDecimal.valueOf(50))
                .build();

        productRepository.save(product);
        productId = product.getId();
    }

    @Test
    void shouldCreateLinkSuccessfully() throws Exception {
        ProductRawMaterialDTOReq req =
                new ProductRawMaterialDTOReq(rawMaterialId, 10);

        mockMvc.perform(post("/api/v1/products/{id}/materials", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.requiredQuantity").value(10));


        assertEquals(1, productRawMaterialRepository.count());
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        ProductRawMaterialDTOReq req =
                new ProductRawMaterialDTOReq(rawMaterialId, 10);

        mockMvc.perform(post("/api/v1/products/{id}/materials", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenBodyInvalid() throws Exception {
        ProductRawMaterialDTOReq req =
                new ProductRawMaterialDTOReq(null, -5);

        mockMvc.perform(post("/api/v1/products/{id}/materials", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAllowDuplicatedLink() throws Exception {
        ProductRawMaterialDTOReq req =
                new ProductRawMaterialDTOReq(rawMaterialId, 10);


        mockMvc.perform(post("/api/v1/products/{id}/materials", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/api/v1/products/{id}/materials", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }
}