package teste.autoflex.vitorcsouza.prodmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductRawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductRawMaterialService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/materials")
public class ProductRawMaterialController {

    private final ProductRawMaterialService productRawMaterialService;

    @PostMapping
    public ResponseEntity<ProductRawMaterialDTORes> create(@PathVariable UUID productId, @RequestBody @Valid ProductRawMaterialDTOReq productRawMaterialDTOReq, UriComponentsBuilder uriComponentsBuilder) {

        ProductRawMaterialDTORes saved = productRawMaterialService.link(productId, productRawMaterialDTOReq);

        URI uri = uriComponentsBuilder.path("/products/{productId}/materials").buildAndExpand(saved.id()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }
}
