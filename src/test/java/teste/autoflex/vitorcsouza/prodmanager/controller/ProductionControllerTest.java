package teste.autoflex.vitorcsouza.prodmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Product;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.ProductRawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRawMaterialRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.ProductRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository productRawMaterialRepository;

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

        Product product = Product.builder()
                .name("Table")
                .price(BigDecimal.valueOf(50))
                .build();

        productRepository.save(product);

        ProductRawMaterial link = ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rm)
                .requiredQuantity(10)
                .build();

        productRawMaterialRepository.save(link);
    }

    @Test
    void shouldSimulateProduction() throws Exception {
        mockMvc.perform(get("/api/v1/production/simulate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Table"))
                .andExpect(jsonPath("$[0].maxProduction").value(10))
                .andExpect(jsonPath("$[0].multiply").value(500));
    }

    @Test
    void shouldReturnProductionReport() throws Exception {
        mockMvc.perform(get("/api/v1/production/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productsCount").value(1))
                .andExpect(jsonPath("$.totalUnits").value(10))
                .andExpect(jsonPath("$.totalRevenue").value(500));
    }

    @Test
    void shouldReturnEmptySimulationWhenNoData() throws Exception {
        productRawMaterialRepository.deleteAll();
        productRepository.deleteAll();
        rawMaterialRepository.deleteAll();

        mockMvc.perform(get("/api/v1/production/simulate"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}