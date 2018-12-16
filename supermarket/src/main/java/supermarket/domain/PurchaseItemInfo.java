package supermarket.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseItemInfo {
    @JsonProperty
    private String sku;
    @JsonProperty
    private String description;
    @JsonProperty
    private int price;
    @JsonProperty
    private int quantity;
    @JsonProperty
    private int totalPrice;
}

