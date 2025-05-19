package co.com.bancolombia.r2dbc.product;

import co.com.bancolombia.model.branchproductstock.BranchProductStock;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import co.com.bancolombia.r2dbc.product.projection.BranchProductProjection;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
class ProductRepositoryAdapter extends ReactiveAdapterOperations<
    Product,
    ProductEntity,
    Long,
    ProductReactiveRepository
    > implements ProductsRepository {

  private static final String MESSAGE_NOT_FOUND = "Product not found for the update!";
  private static final String LOG_MESSAGE = "{}, {}";

  public ProductRepositoryAdapter(ProductReactiveRepository repository, ObjectMapper mapper) {
    /**
     *  Could be use mapper.mapBuilder if your domain model implement builder pattern
     *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
     *  Or using mapper.map with the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, Product.class));
  }

  @Override
  public Mono<Product> saveProduct(Product product) {
    return super.save(product)
        .onErrorResume(err -> {
          log.error("{}", err.getMessage());
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Mono<Product> updateProductName(Product product) {
    return repository.findById(product.getId())
        .switchIfEmpty(Mono.error(new BusinessException(MESSAGE_NOT_FOUND)))
        .flatMap(entity -> {
          product.setIdBranch(entity.getIdBranch());
          product.setPrice(entity.getPrice());
          product.setStock(entity.getStock());
          return super.save(product);
        }).doOnSuccess(success -> log.info("Updated name to {}:", success.getName()))
        .onErrorResume(err -> {
          log.error(LOG_MESSAGE, err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Mono<Product> updateProductStock(Product product) {
    return repository.findById(product.getId())
        .switchIfEmpty(Mono.error(new BusinessException(MESSAGE_NOT_FOUND)))
        .flatMap(entity -> {
          product.setIdBranch(entity.getIdBranch());
          product.setPrice(entity.getPrice());
          product.setName(entity.getName());
          return super.save(product);
        }).doOnSuccess(success -> log.info("Updated Stock to {}:", success.getStock()))
        .onErrorResume(err -> {
          log.error(LOG_MESSAGE, err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Mono<Void> deleteProduct(Long id) {
    return repository.findById(id)
        .switchIfEmpty(Mono.error(new BusinessException(MESSAGE_NOT_FOUND)))
        .flatMap(productEntity ->
            super.repository.deleteById(productEntity.getId())
        )
        .doOnSuccess(success -> log.info("Product with id {} was deleted!", id))
        .onErrorResume(err -> {
          log.error(LOG_MESSAGE, err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Flux<BranchProductStock> findTopByBranchIdOrderByStock(Long id) {
    return repository.findTopStockProductsByFranchiseId(id)
        .switchIfEmpty(Mono.error(
            new BusinessException("This franchise don't have products associate")))
        .map(this::buildProduct)
        .onErrorResume(err -> {
          log.error(LOG_MESSAGE, err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  private BranchProductStock buildProduct(BranchProductProjection productProjection) {
    return BranchProductStock.builder()
        .branchId(productProjection.getIdBranch())
        .productId(productProjection.getId())
        .productName(productProjection.getName())
        .productPrice(productProjection.getPrice())
        .stock(productProjection.getStock())
        .build();
  }
}
