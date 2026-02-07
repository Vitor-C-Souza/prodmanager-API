package teste.autoflex.vitorcsouza.prodmanager.domain.service;

import teste.autoflex.vitorcsouza.prodmanager.domain.dto.*;

import java.util.UUID;

public interface AuthService {
    AuthRes login(AuthReq req);

    UserDTORes register(UserDTOReq dto);

    UpdateAuthRes updateUser(UUID userId, UpdateAuthReq req);
}
