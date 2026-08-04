package ngokynguyen.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@NoArgsConstructor
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 10)
    private String size;

    @Column(length = 30)
    private String color;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Column(nullable = false)
    private Integer status = 1;

    @PrePersist
    protected void onCreate() {
        if (quantity == null) {
            quantity = 0;
        }

        if (status == null) {
            status = 1;
        }
    }
}