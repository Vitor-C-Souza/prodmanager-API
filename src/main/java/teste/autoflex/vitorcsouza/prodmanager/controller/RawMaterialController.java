package teste.autoflex.vitorcsouza.prodmanager.controller;

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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @PostMapping
    public ResponseEntity<RawMaterialDTORes> create(@RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq, UriComponentsBuilder uriComponentsBuilder) {

        RawMaterialDTORes saved = rawMaterialService.save(rawMaterialDTOReq);

        URI uri = uriComponentsBuilder.path("/raw-materials/{id}").buildAndExpand(saved.id()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDTORes>> findAll() {
        List<RawMaterialDTORes> all = rawMaterialService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTORes> findById(@PathVariable UUID id) {
        RawMaterialDTORes rawMaterial = rawMaterialService.findById(id);
        return ResponseEntity.ok(rawMaterial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        rawMaterialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialDTORes> update(@PathVariable UUID id, @RequestBody @Valid RawMaterialDTOReq rawMaterialDTOReq) {
        RawMaterialDTORes updated = rawMaterialService.update(id, rawMaterialDTOReq);
        return ResponseEntity.ok(updated);
    }
}
