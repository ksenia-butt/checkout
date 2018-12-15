package supermarket.jpa;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price_per_quantity")
    private Integer pricePerQuantity;
    @OneToOne
    @JoinColumn(name="sku")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(Integer pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
    }
}
