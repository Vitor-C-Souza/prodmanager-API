package teste.autoflex.vitorcsouza.prodmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.RawMaterial;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.RawMaterialRepository;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RawMaterialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID rawMaterialId;

    @BeforeEach
    void setup() {
        rawMaterialRepository.deleteAll();

        RawMaterial rm = RawMaterial.builder()
                .name("Steel")
                .code("STL-01")
                .stockQuantity(100)
                .build();

        rawMaterialId = rawMaterialRepository.save(rm).getId();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateRawMaterial() throws Exception {
        var request = Map.of(
                "name", "Plastic",
                "code", "PLS-01",
                "stockQuantity", 50
        );

        mockMvc.perform(post("/api/v1/raw-materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Plastic"))
                .andExpect(jsonPath("$.stockQuantity").value(50));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindAllRawMaterials() throws Exception {
        mockMvc.perform(get("/api/v1/raw-materials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Steel"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindRawMaterialById() throws Exception {
        mockMvc.perform(get("/api/v1/raw-materials/{id}", rawMaterialId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Steel"))
                .andExpect(jsonPath("$.stockQuantity").value(100));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateRawMaterial() throws Exception {
        var request = Map.of(
                "name", "Steel Updated",
                "code", "STL-01",
                "stockQuantity", 100
        );

        mockMvc.perform(put("/api/v1/raw-materials/{id}", rawMaterialId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Steel Updated"))
                .andExpect(jsonPath("$.stockQuantity").value(100));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateStockOnly() throws Exception {
        var request = Map.of(
                "quantity", 30
        );

        mockMvc.perform(patch("/api/v1/raw-materials/{id}/stock", rawMaterialId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockQuantity").value(130));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteRawMaterial() throws Exception {
        mockMvc.perform(delete("/api/v1/raw-materials/{id}", rawMaterialId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/raw-materials/{id}", rawMaterialId))
                .andExpect(status().isNotFound());
    }
}