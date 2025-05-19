package co.com.bancolombia.usecase.branchproductstock;

import co.com.bancolombia.model.branchproductstock.BranchProductStock;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class BranchProductStockUseCase {

  private final ProductsRepository productRepository;

  public Flux<BranchProductStock> findTopStockProductsByFranchise(Long idFranchise) {
    return productRepository.findTopByBranchIdOrderByStock(idFranchise);
  }

}
