package co.com.bancolombia.model.products.gateways;

import co.com.bancolombia.model.products.Product;
import reactor.core.publisher.Mono;

public interface ProductsRepository {

  Mono<Product> saveProduct(Product product);

  Mono<Product> updateProductName(Product product);

  Mono<Product> updateProductStock(Product product);

  Mono<Void> deleteProduct(Long id);

  Mono<Product> findTopByBranchIdOrderByStockDesc(Long idBranch);
}
