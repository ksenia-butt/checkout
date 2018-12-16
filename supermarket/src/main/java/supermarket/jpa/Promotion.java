package supermarket.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price_per_quantity")
    private Integer pricePerQuantity;
    @OneToOne
    @JoinColumn(name = "sku")
    private Product product;
}
