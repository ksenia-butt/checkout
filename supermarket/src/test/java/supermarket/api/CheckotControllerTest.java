package supermarket.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import supermarket.config.GlobalExceptionHandler;
import supermarket.domain.TotalPriceResponse;
import supermarket.enums.Currency;
import supermarket.exception.ProductNotFoundException;
import supermarket.services.CheckoutService;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CheckotControllerTest {
    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    CheckoutController checkoutController;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(checkoutController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void givenInvalidRequestParams_whenGetTotalPrice_thenReturn400() throws Exception {
        mockMvc.perform(get("/totalPrice")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));

        verifyNoMoreInteractions(checkoutService);
    }

    @Test
    public void givenProductDoesNotExist_whenGetTotalPrice_thenReturn400() throws Exception {
        when(checkoutService.calculateTotalPrice(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/totalPrice").param("items", "[\"A\"]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));

        verify(checkoutService, times(1)).calculateTotalPrice(anyList());
    }

    @Test
    public void givenRuntimeExceptionOccurs_whenGetTotalPrice_thenReturn500() throws Exception {
        when(checkoutService.calculateTotalPrice(any())).thenThrow(RuntimeException.class);
        mockMvc.perform(get("/totalPrice").param("items", "[\"A\"]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", equalTo("Internal Error")));

        verify(checkoutService, times(1)).calculateTotalPrice(anyList());
    }

    @Test
    public void givenSuccessRequestCompletion_whenGetTotalPrice_thenReturn200() throws Exception {
        TotalPriceResponse response = TotalPriceResponse
                .builder()
                .totalPrice(70)
                .currency(Currency.GBP)
                .build();
        when(checkoutService.calculateTotalPrice(any())).thenReturn(response);
        mockMvc.perform(get("/totalPrice").param("items", "[\"A\"]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", equalTo(70)))
                .andExpect(jsonPath("$.currency", equalTo("GBP")));


        verify(checkoutService, times(1)).calculateTotalPrice(anyList());
    }

}
