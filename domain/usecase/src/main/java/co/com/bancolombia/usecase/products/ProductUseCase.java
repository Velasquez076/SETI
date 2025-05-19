package co.com.bancolombia.usecase.products;

import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

  private final ProductsRepository productsRepository;

  public Mono<Product> saveProduct(Product product) {
    product.validations();
    return productsRepository.saveProduct(product);
  }

  public Mono<Product> updateProductName(Product product) {
    product.validateId();
    product.validateName();
    return productsRepository.updateProductName(product);
  }

  public Mono<Product> updateProductStock(Product product) {
    product.validateId();
    product.validateStock();
    return productsRepository.updateProductStock(product);
  }

  public Mono<Void> deleteProductById(Product product) {
    product.validateId();
    return productsRepository.deleteProduct(product.getId());
  }
}
