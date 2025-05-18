package co.com.bancolombia.usecase.products;

import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

  private final ProductsRepository productsRepository;

  public Mono<Product> saveProduct(Product product) {
    product.validations(product);
    return productsRepository.saveProduct(product);
  }

  public Mono<Product> updateProductName(Product product) {
    product.validateId(product.getId());
    product.validateName(product.getName());
    return productsRepository.updateProductName(product);
  }

  public Mono<Product> updateProductStock(Product product) {
    product.validateId(product.getId());
    product.validateStock(product.getStock());
    return productsRepository.updateProductStock(product);
  }
}
