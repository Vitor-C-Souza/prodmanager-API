package teste.autoflex.vitorcsouza.prodmanager.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "PRODUCT_RAW_MATERIAL")
@NoArgsConstructor
@Data
public class ProductRawMaterial {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private int required_quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;
}
