package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Calculated result of production capacity for a specific product")
public record ProductionResultDTO(
        @Schema(description = "Name of the product being analyzed", example = "Mechanical Keyboard G-Pro")
        String name,
        @Schema(description = "Maximum number of units that can be produced based on raw material availability", example = "42")
        int maxProduction,
        @Schema(description = "Current unit price of the product", example = "599.90")
        BigDecimal price,
        @Schema(description = "Multiplication factor or total value calculation", example = "25195.80")
        BigDecimal multiply) {
}
