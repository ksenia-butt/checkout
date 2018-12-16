package supermarket.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NamedEntityGraph(name = "graph.product.promotion",
        attributeNodes = @NamedAttributeNode(value = "promotion", subgraph = "promotion"))
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "sku", nullable = false)
    private String sku;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "price")
    private Integer price;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Promotion promotion;
}
