package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import java.util.List;
import java.util.UUID;

public interface CrudBase<DTORes, DTOReq> {

    DTORes save(DTOReq dto);

    List<DTORes> findAll();

    DTORes findById(UUID id);

    void deleteById(UUID id);
}
