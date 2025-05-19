package co.com.bancolombia.usecase.products;

import static org.mockito.Mockito.doReturn;

import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

  @Mock
  ProductsRepository productsRepository;

  @InjectMocks
  ProductUseCase productUseCase;

  private static final Product PRODUCT = new Product(1L, "name", 1500D, 150L);

  @Test
  void saveProductTest() {
    doReturn(Mono.just(PRODUCT)).when(productsRepository)
        .saveProduct(Mockito.any(Product.class));
    var result = productUseCase.saveProduct(PRODUCT);

    StepVerifier.create(result)
        .expectNext(PRODUCT)
        .verifyComplete();

    Mockito.verify(productsRepository)
        .saveProduct(Mockito.any(Product.class));
  }

  @Test
  void updateProductNameTest() {
    PRODUCT.setId(1L);
    doReturn(Mono.just(PRODUCT)).when(productsRepository)
        .updateProductName(Mockito.any(Product.class));
    var result = productUseCase.updateProductName(PRODUCT);

    StepVerifier.create(result)
        .expectNext(PRODUCT)
        .verifyComplete();

    Mockito.verify(productsRepository)
        .updateProductName(Mockito.any(Product.class));
  }

  @Test
  void updateProductStockTest() {
    PRODUCT.setId(1L);
    doReturn(Mono.just(PRODUCT)).when(productsRepository)
        .updateProductStock(Mockito.any(Product.class));
    var result = productUseCase.updateProductStock(PRODUCT);

    StepVerifier.create(result)
        .expectNext(PRODUCT)
        .verifyComplete();

    Mockito.verify(productsRepository)
        .updateProductStock(Mockito.any(Product.class));
  }

  @Test
  void deleteProductById() {
    PRODUCT.setId(1L);
    doReturn(Mono.empty()).when(productsRepository)
        .deleteProduct(Mockito.anyLong());
    var result = productUseCase.deleteProductById(PRODUCT);

    StepVerifier.create(result)
        .expectNext()
        .verifyComplete();

    Mockito.verify(productsRepository)
        .deleteProduct(Mockito.anyLong());
  }
}