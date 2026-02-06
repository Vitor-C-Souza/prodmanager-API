package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import java.math.BigDecimal;

public record ProductionResultDTO(
        String name,
        int maxProduction,
        BigDecimal price,
        BigDecimal multiply) {
}
