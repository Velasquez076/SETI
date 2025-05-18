package co.com.bancolombia.r2dbc.product;

import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>,
    ReactiveQueryByExampleExecutor<ProductEntity> {

}
