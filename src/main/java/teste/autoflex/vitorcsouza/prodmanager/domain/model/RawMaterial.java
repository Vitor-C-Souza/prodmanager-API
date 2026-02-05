package teste.autoflex.vitorcsouza.prodmanager.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RAW_MATERIAL")
@NoArgsConstructor
@Data
public class RawMaterial {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String name;
    private String code;
    private int stock_quantity;

    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
    private List<ProductRawMaterial> materials;
}
