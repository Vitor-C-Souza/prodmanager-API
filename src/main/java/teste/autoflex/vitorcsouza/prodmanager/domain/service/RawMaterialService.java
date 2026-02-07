package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTORes;

import java.util.UUID;

public interface RawMaterialService extends CrudBase<RawMaterialDTORes, RawMaterialDTOReq> {
    RawMaterialDTORes updateStock(UUID id, int quantity);
}
