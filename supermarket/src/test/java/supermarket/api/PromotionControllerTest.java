package supermarket.api;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import supermarket.config.GlobalExceptionHandler;
import supermarket.exception.ProductNotFoundException;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;
import supermarket.services.PromotionService;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class PromotionControllerTest {
    @Mock
    private PromotionService promotionService;

    @InjectMocks
    PromotionController promotionController;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(promotionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void givenInvalidRequest_whenAddPromotion_shouldReturn400() throws Exception {
        String request1 = "{\"sku\": null, \"quantity\": 2, \"pricePerQuantity\": 10}";
        String request2 = "{\"sku\": \"A\", \"quantity\": null, \"pricePerQuantity\": 10}";
        String request3 = "{\"sku\": \"A\", \"quantity\": 2, \"pricePerQuantity\": 0}";

        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content( request3))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));

        verifyNoMoreInteractions(promotionService);
    }

    @Test
    public void givenProductDoesNotExist_whenAddPromotion_shouldReturn400() throws Exception {
        String request = "{\"sku\": \"INVALID\", \"quantity\": 2, \"pricePerQuantity\": 10}";

        when(promotionService.addPromotion(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("Bad Request")));

        verify(promotionService, times(1)).addPromotion(any());
    }

    @Test
    public void givenDatabaseIssue_whenAddPromotion_shouldReturn500() throws Exception {
        String request = "{\"sku\": \"A\", \"quantity\": 2, \"pricePerQuantity\": 10}";

        when(promotionService.addPromotion(any())).thenThrow(HibernateException.class);
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", equalTo("Internal Error")));

        verify(promotionService, times(1)).addPromotion(any());
    }

    @Test
    public void givenProductExistsAndRequestValid_whenAddPromotion_shouldReturn200() throws Exception {
        String request = "{\"sku\": \"A\", \"quantity\": 2, \"pricePerQuantity\": 10}";

        when(promotionService.addPromotion(any())).thenReturn(new Promotion(1L, 2, 10, new Product()));
        mockMvc.perform(post("/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());

        verify(promotionService, times(1)).addPromotion(any());
    }
}
