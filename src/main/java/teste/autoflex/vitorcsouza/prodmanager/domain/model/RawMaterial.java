package teste.autoflex.vitorcsouza.prodmanager.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RAW_MATERIAL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RawMaterial {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String name;
    private String code;
    private int stockQuantity;

    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
    private List<ProductRawMaterial> materials;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
