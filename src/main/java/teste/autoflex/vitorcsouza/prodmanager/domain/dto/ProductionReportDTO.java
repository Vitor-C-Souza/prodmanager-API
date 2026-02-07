package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Summary report of production and financial performance")
public record ProductionReportDTO(
        @Schema(description = "Number of distinct product types registered", example = "12")
        int productsCount,
        @Schema(description = "Total number of units produced across all products", example = "450")
        int totalUnits,
        @Schema(description = "Total revenue generated from production", example = "25750.50")
        BigDecimal totalRevenue
) {
}
