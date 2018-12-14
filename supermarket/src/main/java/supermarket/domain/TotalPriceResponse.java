package supermarket.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import supermarket.enums.Currency;

public class TotalPriceResponse {
    @JsonProperty
    private int totalPrice;
    @JsonProperty
    private Currency currency;

    public TotalPriceResponse(int totalPrice) {
        this.totalPrice = totalPrice;
        this.currency = Currency.GBP;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
