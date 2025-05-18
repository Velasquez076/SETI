package co.com.bancolombia.r2dbc.product;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class ProductRepositoryAdapter extends ReactiveAdapterOperations<
    Product,
    ProductEntity,
    Long,
    ProductReactiveRepository
    > implements ProductsRepository {

  private static final String MESSAGE_NOT_FOUND = "Product not found for the update!";

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
          log.error("{}, {}", err.getMessage(), err);
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
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }
}
