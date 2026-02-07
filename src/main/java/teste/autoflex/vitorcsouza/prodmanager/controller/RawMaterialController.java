package teste.autoflex.vitorcsouza.prodmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @Operation(
            summary = "Create a new raw material",
            description = "Registers a new raw material in the system"
    )
    @PostMapping
    public ResponseEntity<RawMaterialDTORes> create(@RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq, UriComponentsBuilder uriComponentsBuilder) {

        RawMaterialDTORes saved = rawMaterialService.save(rawMaterialDTOReq);

        URI uri = uriComponentsBuilder.path("/raw-materials/{id}").buildAndExpand(saved.id()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @Operation(
            summary = "List all raw materials",
            description = "Returns a list with all registered raw materials"
    )
    @GetMapping
    public ResponseEntity<List<RawMaterialDTORes>> findAll() {
        List<RawMaterialDTORes> all = rawMaterialService.findAll();
        return ResponseEntity.ok(all);
    }

    @Operation(
            summary = "Create a new raw material",
            description = "Registers a new raw material in the system"
    )
    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTORes> findById(@PathVariable UUID id) {
        RawMaterialDTORes rawMaterial = rawMaterialService.findById(id);
        return ResponseEntity.ok(rawMaterial);
    }

    @Operation(
            summary = "Find raw material by ID",
            description = "Returns a raw material by its UUID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        rawMaterialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete raw material",
            description = "Removes a raw material from the system"
    )
    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialDTORes> update(@PathVariable UUID id, @RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq) {
        RawMaterialDTORes updated = rawMaterialService.update(id, rawMaterialDTOReq);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Update stock quantity",
            description = "Updates the stock quantity of a raw material"
    )
    @PutMapping("/{id}/stock")
    public ResponseEntity<RawMaterialDTORes> updateStock(@PathVariable UUID id, @RequestBody Map<String, Integer> value) {
        Integer quantity = value.get("quantity");
        RawMaterialDTORes updatedStock = rawMaterialService.updateStock(id, quantity);
        return ResponseEntity.ok(updatedStock);
    }
}
