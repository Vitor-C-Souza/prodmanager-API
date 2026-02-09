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
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTOReq;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.RawMaterialDTORes;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.RawMaterialService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/raw-materials")
@Tag(
        name = "Raw Materials",
        description = "Endpoints for managing raw materials and stock"
)
@SecurityRequirement(name = "bearerAuth")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @Operation(
            summary = "Create a new raw material",
            description = "Registers a new raw material in the system"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RawMaterialDTORes> create(@RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq, UriComponentsBuilder uriComponentsBuilder) {
        RawMaterialDTORes saved = rawMaterialService.save(rawMaterialDTOReq);
        URI uri = uriComponentsBuilder.path("/api/v1/raw-materials/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @Operation(
            summary = "List all raw materials",
            description = "Returns a list with all registered raw materials"
    )
    @GetMapping
    public ResponseEntity<List<RawMaterialDTORes>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @Operation(
            summary = "Find raw material by ID",
            description = "Returns a single raw material by its UUID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTORes> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(rawMaterialService.findById(id));
    }

    @Operation(
            summary = "Delete raw material",
            description = "Removes a raw material from the system permanently"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        rawMaterialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update raw material",
            description = "Updates the name and code of an existing raw material"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RawMaterialDTORes> update(@PathVariable UUID id, @RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq) {
        return ResponseEntity.ok(rawMaterialService.update(id, rawMaterialDTOReq));
    }

    @Operation(
            summary = "Update stock quantity",
            description = "Updates the current stock level for a specific material"
    )
    @PatchMapping("/{id}/stock")
    public ResponseEntity<RawMaterialDTORes> updateStock(@PathVariable UUID id, @RequestBody Map<String, Integer> value) {
        Integer quantity = value.get("quantity");
        return ResponseEntity.ok(rawMaterialService.updateStock(id, quantity));
    }
}