package co.com.bancolombia.usecase.branchproductstock;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.branchproductstock.BranchProductStock;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class BranchProductStockUseCase {

  private final ProductsRepository productRepository;
  private final BranchRepository branchRepository;

  public Flux<BranchProductStock> findTopStockProductsByFranchise(Long franchiseId) {
    return branchRepository.findByFranchiseId(franchiseId)
        .flatMap(branch ->
            productRepository.findTopByBranchIdOrderByStockDesc(branch.getId())
                .map(product -> mapToResponse(branch, product))
        );
  }

  private BranchProductStock mapToResponse(Branch branch, Product product) {
    return new BranchProductStock(
        branch.getId(),
        branch.getName(),
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getStock()
    );
  }
}
