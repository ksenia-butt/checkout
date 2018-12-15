package supermarket.jpa;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NamedEntityGraph(name = "graph.product.promotion",
        attributeNodes = @NamedAttributeNode(value = "promotion", subgraph = "promotion"))
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

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
