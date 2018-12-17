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
import supermarket.exception.ProductNotFoundException;
import supermarket.jpa.Promotion;
import supermarket.jpa.PromotionRepository;

import static org.mockito.Mockito.*;

public class PromotionDaoTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private ProductDao productDao;
    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private PromotionDao promotionDao;

    private static final String SKU = "Y";

    @Before
    public void beforeEach() {
        promotionDao = new PromotionDao(promotionRepository, productDao);
    }

    @Test
    public void givenNoPromotionExists_whenAddPromotion_thenSavePromotion() {
        when(productDao.getProduct(SKU)).thenReturn(TestUtils.createProduct(SKU));
        PromotionRequest promotionRequest = new PromotionRequest(SKU, 3, 10);
        Promotion promotion = promotionDao.addPromotion(promotionRequest);
        verify(productDao, times(1)).getProduct(SKU);
        verify(promotionRepository, times(1)).save(any());
        verify(promotionRepository, never()).delete(any());
    }

    @Test
    public void givenPromotionExists_whenAddPromotion_thenDeleteExistingAndSaveNewPromotion() {
        when(productDao.getProduct(SKU)).thenReturn(TestUtils.createProduct(SKU));
        PromotionRequest promotionRequest = new PromotionRequest(SKU, 3, 10);
        Promotion promotion = promotionDao.addPromotion(promotionRequest);
        verify(productDao, times(1)).getProduct(SKU);
        verify(promotionRepository, times(1)).save(any());
        verify(promotionRepository, never()).delete(any());
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenNoProductExists_whenAddPromotion_thenShouldThrowProductNotFoundException() {
        when(productDao.getProduct(SKU)).thenThrow(ProductNotFoundException.class);
        PromotionRequest promotionRequest = new PromotionRequest(SKU, 3, 10);
        promotionDao.addPromotion(promotionRequest);
        verify(productDao, times(1)).getProduct(SKU);
        verify(promotionRepository, never()).save(any());
        verify(promotionRepository, never()).delete(any());
    }

}
