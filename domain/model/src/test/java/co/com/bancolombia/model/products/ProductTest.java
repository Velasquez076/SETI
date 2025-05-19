package co.com.bancolombia.model.products;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.util.UtilMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductTest {

  @InjectMocks
  Product product;

  @Test
  void validationsSuccessTest() {
    product = UtilMocks.buildProduct();
    product.validations();
    Assertions.assertNotNull(product);
  }

  @Test
  void validationsExceptionTest() {
    product = new Product(1L, 25L);
    product.setPrice(-1D);
    Assertions.assertThrows(BusinessException.class, () ->
        product.validations()
    );
  }

  @Test
  void validationsExceptionZeroTest() {
    product = new Product(1L, 0L);
    product.setPrice(0D);
    Assertions.assertThrows(BusinessException.class, () ->
        product.validations()
    );
  }

  @Test
  void validateIdNullTest() {
    product = new Product(null);
    var exception = Assertions.assertThrows(BusinessException.class, () -> {
      product.validateId();
    });
    Assertions.assertEquals("Id cannot be null", exception.getMessage());
  }

  @Test
  void validateIdSuccessTest() {
    product = new Product(1L);
    product.validateId();
    Assertions.assertNotNull(product);
  }

  @Test
  void validateStock() {
    product = new Product(1L, -1L);
    var exception = Assertions.assertThrows(BusinessException.class, () -> {
      product.validateStock();
    });
    Assertions.assertEquals("Stock cannot be negative", exception.getMessage());
  }

  @Test
  void validateNameNullTest() {
    product = new Product(1L, (String) null);
    var exception = Assertions.assertThrows(BusinessException.class, () -> {
      product.validateName();
    });
    Assertions.assertEquals("Product name cannot be empty", exception.getMessage());
  }

  @Test
  void validateNameEmptyTest() {
    product = new Product(1L, "  ");
    var exception = Assertions.assertThrows(BusinessException.class, () -> {
      product.validateName();
    });
    Assertions.assertEquals("Product name cannot be empty", exception.getMessage());
  }
}