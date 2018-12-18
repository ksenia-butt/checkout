package supermarket.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequest {
    @JsonProperty(required = true)
    @NotNull
    private String sku;
    @JsonProperty
    @NotNull
    @Min(1)
    private Integer quantity;
    @JsonProperty
    @NotNull
    @Min(1)
    private Integer pricePerQuantity;
}
