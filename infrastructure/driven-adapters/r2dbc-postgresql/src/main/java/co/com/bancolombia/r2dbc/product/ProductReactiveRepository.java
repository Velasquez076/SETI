package co.com.bancolombia.r2dbc.product;

import co.com.bancolombia.r2dbc.product.entity.ProductEntity;
import co.com.bancolombia.r2dbc.product.projection.BranchProductProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>,
    ReactiveQueryByExampleExecutor<ProductEntity> {

  @Query(value = """
      SELECT p.id_product as id_product, p.name as name,
            p.price as price, p.stock as stock,
            b.id_branch
      FROM products p
         JOIN branches b ON p.id_branch = b.id_branch
      WHERE b.id_franchise = :franchiseId
          AND (p.id_branch, p.stock) IN (SELECT id_branch, MAX(stock)
      FROM products GROUP BY id_branch);
      """)
  Flux<BranchProductProjection> findTopStockProductsByFranchiseId(
      @Param("franchiseId") Long franchiseId);
}
