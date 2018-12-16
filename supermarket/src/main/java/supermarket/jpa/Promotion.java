package supermarket.jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
