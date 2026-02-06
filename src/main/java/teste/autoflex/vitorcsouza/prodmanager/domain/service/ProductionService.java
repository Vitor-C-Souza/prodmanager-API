package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductionResultDTO;

import java.util.List;

public interface ProductionService {
    List<ProductionResultDTO> calculateProduction();
}
