package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTORes;

import java.util.UUID;

public interface ProductService extends CrudBase<ProductDTORes, ProductDTOReq> {
    void removeMaterial(UUID productId, UUID rawMaterialId);
}
