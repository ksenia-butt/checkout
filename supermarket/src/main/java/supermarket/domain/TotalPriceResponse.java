package supermarket.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import supermarket.enums.Currency;

import java.util.List;

@Getter
@Setter
@Builder
public class TotalPriceResponse {
    @JsonProperty
    private int totalPrice;
    @JsonProperty
    private Currency currency;
    @JsonProperty
    private List<AppliedPromotion> appliedPromotions;
    @JsonProperty
    private List<PurchaseItemInfo> purchasedItems;
}
