package supermarket.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import supermarket.TestUtils;
import supermarket.domain.PromotionRequest;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PromotionServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private PromotionDao promotionDao;
    @InjectMocks
    private PromotionServiceImpl promotionService;

    private static final String SKU = "X";

    @Before
    public void beforeEach() {
        promotionService = new PromotionServiceImpl(promotionDao);
    }

    @Test
    public void whenAddPromotion_thenShouldReturnNewPromotion() {
        PromotionRequest promotionRequest = new PromotionRequest(SKU, 3, 10);
        Promotion promotion = TestUtils.createPromotion(3, 10, new Product());
        when(promotionDao.addPromotion(promotionRequest)).thenReturn(promotion);
        Promotion savedPromotion = promotionService.addPromotion(promotionRequest);
        verify(promotionDao, times(1)).addPromotion(promotionRequest);
        assertThat(savedPromotion, is(notNullValue()));
        assertThat(savedPromotion.getId(), is(promotion.getId()));
        assertThat(savedPromotion.getQuantity(), is(promotion.getQuantity()));
        assertThat(savedPromotion.getPricePerQuantity(), is(promotion.getPricePerQuantity()));
    }
}
