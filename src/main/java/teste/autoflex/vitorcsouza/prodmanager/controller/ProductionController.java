package teste.autoflex.vitorcsouza.prodmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductionReportDTO;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductionResultDTO;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/production")
@Tag(name = "Production", description = "Production simulation and reporting")
public class ProductionController {

    private final ProductionService productionService;

    @Operation(summary = "Simulate production",
            description = "Calculates the maximum production based on available raw materials")
    @GetMapping("/simulate")
    public ResponseEntity<List<ProductionResultDTO>> simulate() {
        List<ProductionResultDTO> calculated = productionService.calculateProduction();

        return ResponseEntity.ok(calculated);
    }

    @Operation(summary = "Simulate production",
            description = "Calculates the maximum production based on available raw materials")
    @GetMapping("/report")
    public ResponseEntity<ProductionReportDTO> report() {
        List<ProductionResultDTO> items = productionService.calculateProduction();

        int totalUnits = items.stream().mapToInt(ProductionResultDTO::maxProduction).sum();

        BigDecimal totalRevenue = items.stream().map(ProductionResultDTO::multiply).reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(new ProductionReportDTO(items.size(), totalUnits, totalRevenue));
    }
}
