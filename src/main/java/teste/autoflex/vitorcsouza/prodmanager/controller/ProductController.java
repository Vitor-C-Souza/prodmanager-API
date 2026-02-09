package teste.autoflex.vitorcsouza.prodmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Products",
        description = "Endpoints for managing products"
)
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Registers a new product in the system"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTORes> create(@RequestBody @Valid ProductDTOReq productDTOReq, UriComponentsBuilder uriComponentsBuilder) {

        ProductDTORes saved = productService.save(productDTOReq);

        URI uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(saved.id()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @Operation(
            summary = "List all products",
            description = "Returns a list with all registered products"
    )
    @GetMapping
    public ResponseEntity<List<ProductDTORes>> findAll() {
        List<ProductDTORes> all = productService.findAll();
        return ResponseEntity.ok(all);
    }

    @Operation(
            summary = "Find product by ID",
            description = "Returns a product by its UUID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTORes> findById(@PathVariable UUID id) {
        ProductDTORes product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Delete product",
            description = "Removes a product from the system"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update product",
            description = "Updates the data of an existing product"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTORes> update(@PathVariable UUID id, @RequestBody @Valid ProductDTOReq productDTOReq) {
        ProductDTORes updated = productService.update(id, productDTOReq);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{productId}/materials/{relationshipId}")
    public ResponseEntity<Void> removeMaterialFromProduct(
            @PathVariable UUID productId,
            @PathVariable UUID relationshipId) {
        productService.removeMaterial(productId, relationshipId);
        return ResponseEntity.noContent().build();
    }
}
