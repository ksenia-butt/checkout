package supermarket.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
@Getter
@Setter
public class Promotion {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price_per_quantity")
    private Integer pricePerQuantity;
    @OneToOne
    @JoinColumn(name = "sku")
    private Product product;
}
