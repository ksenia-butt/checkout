package supermarket.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import supermarket.TestUtils;
import supermarket.exception.ProductNotFoundException;
import supermarket.jpa.Product;
import supermarket.jpa.ProductRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProductDaoTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductDao productDao;

    private static final String SKU = "Z";

    @Before
    public void beforeEach() {
        productDao = new ProductDao(productRepository);
    }

    @Test
    public void givenProductExists_whenGetProduct_thenShouldReturnProduct() {
        when(productRepository.findBySku(SKU)).thenReturn(TestUtils.createProduct(SKU));
        Product product = productDao.getProduct(SKU);
        verify(productRepository, times(1)).findBySku(SKU);
        assertThat(product.getSku(), is(SKU));
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenProductDoesntExists_whenGetProduct_thenShouldThrowProductNotFoundException() {
        when(productRepository.findBySku(SKU)).thenReturn(null);
        productDao.getProduct(SKU);
        verify(productRepository, times(1)).findBySku(SKU);
    }
}
