package teste.autoflex.vitorcsouza.prodmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.ProductDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTORes> create(@RequestBody @Valid ProductDTOReq productDTOReq, UriComponentsBuilder uriComponentsBuilder) {

        ProductDTORes saved = productService.save(productDTOReq);

        URI uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(saved.id()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTORes>> findAll() {
        List<ProductDTORes> all = productService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTORes> findById(@PathVariable UUID id) {
        ProductDTORes product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
