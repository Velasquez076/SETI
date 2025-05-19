package co.com.bancolombia.r2dbc.product;

import static org.mockito.Mockito.when;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import co.com.bancolombia.r2dbc.product.projection.BranchProductProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
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

  Product product = new Product(1L, "Test Product", 100.0, 10L);
  ProductEntity entity = new ProductEntity(1L, 1L, "Test Product Entity", 100.0, 10L);
  RuntimeException error = new RuntimeException("Database error");

  private static final String DB_ERR_MSG = "Database error";

  @Test
  void saveProductSuccessTest() {

    when(mapper.map(product, ProductEntity.class)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Product.class)).thenReturn(product);

    var result = productRepositoryAdapter.saveProduct(product);

    StepVerifier.create(result)
        .expectNext(product)
        .verifyComplete();
  }

  @Test
  void saveProductTechnicalExceptionTest() {

    when(mapper.map(product, ProductEntity.class)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.error(error));

    var result = productRepositoryAdapter.saveProduct(product);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  @Test
  void updateProductNameSuccessTest() {

    when(repository.findById(1L)).thenReturn(Mono.just(entity));
    when(mapper.map(product, ProductEntity.class)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Product.class)).thenReturn(product);

    product.setId(1L);
    product.setName("tomato");
    var result = productRepositoryAdapter.updateProductName(product);

    StepVerifier.create(result)
        .expectNext(product)
        .verifyComplete();
    Assertions.assertNotNull(result);
  }

  @Test
  void updateNameTechnicalExceptionTest() {
    when(repository.findById(1L)).thenReturn(Mono.error(error));

    product.setId(1L);
    var result = productRepositoryAdapter.updateProductName(product);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  @Test
  void updateProductStockSuccessTest() {
    when(repository.findById(1L)).thenReturn(Mono.just(entity));
    when(mapper.map(product, ProductEntity.class)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Product.class)).thenReturn(product);

    product.setId(1L);
    product.setStock(145L);
    var result = productRepositoryAdapter.updateProductStock(product);

    StepVerifier.create(result)
        .expectNext(product)
        .verifyComplete();
    Assertions.assertNotNull(result);
  }

  @Test
  void updateStockTechnicalExceptionTest() {
    when(repository.findById(1L)).thenReturn(Mono.error(error));
    product.setId(1L);
    var result = productRepositoryAdapter.updateProductStock(product);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  @Test
  void deleteProductByIdTest() {
    when(repository.findById(1L)).thenReturn(Mono.just(entity));
    when(repository.deleteById(1L)).thenReturn(Mono.empty());

    var result = productRepositoryAdapter.deleteProduct(1L);
    StepVerifier.create(result)
        .expectNext()
        .verifyComplete();
  }

  @Test
  void deleteProductByExceptionTest() {
    when(repository.findById(1L)).thenReturn(Mono.just(entity));
    when(repository.deleteById(1L)).thenReturn(Mono.error(error));

    var result = productRepositoryAdapter.deleteProduct(1L);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  @Test
  void findTopByBranchIdOrderByStockSuccessTest() {
    when(repository.findTopStockProductsByFranchiseId(1L)).thenReturn(Flux.just(buildProjection()));

    var result = productRepositoryAdapter.findTopByBranchIdOrderByStock(1L);

    StepVerifier.create(result)
        .expectNext()
        .expectNextCount(1L)
        .verifyComplete();
  }

  @Test
  void findTopByBranchIdOrderByStockExceptionTest() {
    when(repository.findTopStockProductsByFranchiseId(1L)).thenReturn(Flux.error(error));

    var result = productRepositoryAdapter.findTopByBranchIdOrderByStock(1L);

    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  BranchProductProjection buildProjection() {
    return new BranchProductProjection() {
      @Override
      public Long getId() {
        return 0L;
      }

      @Override
      public String getName() {
        return "product-name";
      }

      @Override
      public Double getPrice() {
        return 0.0;
      }

      @Override
      public Long getStock() {
        return 15L;
      }

      @Override
      public Long getIdBranch() {
        return 1L;
      }
    };
  }
}