package supermarket.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import supermarket.TestUtils;
import supermarket.domain.AppliedPromotion;
import supermarket.domain.TotalPriceResponse;
import supermarket.enums.Currency;
import supermarket.exception.ProductNotFoundException;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;

import java.util.Arrays;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CheckoutServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private CheckoutServiceImpl checkoutService;


    @Before
    public void beforeEach() {
        checkoutService = new CheckoutServiceImpl(productDao);
        when(productDao.getProduct("A")).thenReturn(new Product("A", "Apples", 10, null));
        when(productDao.getProduct("B")).thenReturn(new Product("B", "Bananas", 20, null));
        Product productC = TestUtils.createProduct("C", "Clementines", 30);
        Promotion promotion = TestUtils.createPromotion(3, 60, productC);
        productC.setPromotion(promotion);
        when(productDao.getProduct("C")).thenReturn(productC);
    }

    @Test
    public void givenNoPromosAttachedToProducts_whenCalculateTotalPrice_thenShouldAggregateAndReturnTotal() {
        TotalPriceResponse response = checkoutService.calculateTotalPrice(Arrays.asList("A", "A", "A", "B", "B"));
        verify(productDao, times(1)).getProduct("A");
        verify(productDao, times(1)).getProduct("B");
        assertThat(response.getAppliedPromotions(), is(emptyCollectionOf(AppliedPromotion.class)));
        assertThat(response.getCurrency(), is(Currency.GBP));
        assertThat(response.getPurchasedItems().size(), is(2));
        //total calculated as: 10*3 + 20*2 = 70
        assertThat(response.getTotalPrice(), is(70));
    }

    @Test
    public void givenPromoAttached_whenCalcTotalForNotQualifyingQuantity_thenShouldNotApplyPromoToTotal() {
        TotalPriceResponse response = checkoutService.calculateTotalPrice(Arrays.asList("A", "A", "C", "C"));
        verify(productDao, times(1)).getProduct("C");
        verify(productDao, times(1)).getProduct("A");
        assertThat(response.getAppliedPromotions(), is(emptyCollectionOf(AppliedPromotion.class)));
        assertThat(response.getCurrency(), is(Currency.GBP));
        assertThat(response.getPurchasedItems().size(), is(2));
        //total calculated as: 10*2 + 30*2 = 80
        assertThat(response.getTotalPrice(), is(80));
    }

    @Test
    public void givenPromosAttached_whenCalcTotalForQuantityGreaterThanPromoQuantity_thenShouldApplyPromosAndNormalPrice() {
        TotalPriceResponse response = checkoutService.calculateTotalPrice(Arrays.asList("C", "C", "C", "C"));
        verify(productDao, times(1)).getProduct("C");
        assertThat(response.getAppliedPromotions().size(), is(1));
        assertThat(response.getCurrency(), is(Currency.GBP));
        assertThat(response.getPurchasedItems().size(), is(1));
        //total calculated as: 3 for 60 + 30*1 = 90
        assertThat(response.getTotalPrice(), is(90));
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenOneOfProductsDoesNotExist_whenCalculateTotalPrice_thenShouldReturnProductNotFoundException() {
        when(productDao.getProduct("D")).thenThrow(ProductNotFoundException.class);
        TotalPriceResponse response = checkoutService.calculateTotalPrice(Arrays.asList("A", "A", "C", "C", "D"));
        verify(productDao, times(1)).getProduct("C");
        verify(productDao, times(1)).getProduct("A");
        verify(productDao, times(1)).getProduct("D");
    }
}
