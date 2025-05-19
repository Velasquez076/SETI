package co.com.bancolombia.r2dbc.product;

import static org.mockito.Mockito.when;

import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

  @Mock
  ProductReactiveRepository repository;

  @Mock
  ObjectMapper mapper;

  @InjectMocks
  ProductRepositoryAdapter productRepositoryAdapter;

  @Test
  void saveProduct_WhenSuccess_ShouldReturnSavedProduct() {

    var product = new Product(1L, "Test Product", 100.0, 10L);
    var entity = new ProductEntity(1L, 1L, "Test Product", 100.0, 10L);
    var savedProduct = new Product(1L, "Test Product", 100.0, 10L);

    when(mapper.map(product, ProductEntity.class)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Product.class)).thenReturn(savedProduct);

    var result = productRepositoryAdapter.saveProduct(product);

    StepVerifier.create(result)
        .expectNext(savedProduct)
        .verifyComplete();
  }

  @Test
  void updateProductName() {
  }

  @Test
  void updateProductStock() {
  }

  @Test
  void deleteProduct() {
  }

  @Test
  void findTopByBranchIdOrderByStock() {
  }
}