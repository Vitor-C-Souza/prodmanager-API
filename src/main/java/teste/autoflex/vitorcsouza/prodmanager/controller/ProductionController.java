package teste.autoflex.vitorcsouza.prodmanager.controller;

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
public class ProductionController {

    private final ProductionService productionService;

    @GetMapping("/simulate")
    public ResponseEntity<List<ProductionResultDTO>> simulate() {
        List<ProductionResultDTO> calculated = productionService.calculateProduction();

        return ResponseEntity.ok(calculated);
    }

    @GetMapping("/report")
    public ResponseEntity<ProductionReportDTO> report() {
        List<ProductionResultDTO> items = productionService.calculateProduction();

        int totalUnits = items.stream().mapToInt(ProductionResultDTO::maxProduction).sum();

        BigDecimal totalRevenue = items.stream().map(ProductionResultDTO::multiply).reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(new ProductionReportDTO(items.size(), totalUnits, totalRevenue));
    }
}
