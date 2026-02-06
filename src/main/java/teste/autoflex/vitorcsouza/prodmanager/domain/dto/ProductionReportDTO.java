package teste.autoflex.vitorcsouza.prodmanager.domain.dto;

import java.math.BigDecimal;

public record ProductionReportDTO(
        int productsCount,
        int totalUnits,
        BigDecimal totalRevenue
) {
}
