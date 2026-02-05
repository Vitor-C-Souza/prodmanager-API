package teste.autoflex.vitorcsouza.prodmanager.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String name;
    private String code;
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductRawMaterial>  materials;

}
