package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTORes;

import java.util.UUID;

public interface ProductRawMaterialService {
    ProductRawMaterialDTORes link(UUID productId, ProductRawMaterialDTOReq productRawMaterialDTOReq);
}
