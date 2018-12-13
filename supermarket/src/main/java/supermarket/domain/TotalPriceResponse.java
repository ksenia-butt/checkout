package supermarket.domain;


import supermarket.enums.Currency;

public class TotalPriceResponse {
    private int totalPrice;
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
