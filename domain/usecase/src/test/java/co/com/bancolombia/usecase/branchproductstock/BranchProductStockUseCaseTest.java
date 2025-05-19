package co.com.bancolombia.usecase.branchproductstock;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.products.Product;
import co.com.bancolombia.model.products.gateways.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BranchProductStockUseCaseTest {

  @Mock
  ProductsRepository productRepository;

  @Mock
  BranchRepository branchRepository;

  @InjectMocks
  BranchProductStockUseCase branchProductStockUseCase;

  @Test
  void findTopStockProductsByFranchiseTest() {
    Mockito.doReturn(Flux.just(buildBranch()))
        .when(branchRepository).findByFranchiseId(Mockito.anyLong());

    Mockito.doReturn(Mono.just(buildProduct()))
        .when(productRepository).findTopByBranchIdOrderByStockDesc(Mockito.anyLong());

    var result = branchProductStockUseCase.findTopStockProductsByFranchise(1L);

    StepVerifier.create(result)
        .expectNextCount(1)
        .verifyComplete();
  }

  Product buildProduct() {
    return Product.builder()
        .id(1L)
        .idBranch(1L)
        .stock(25L)
        .price(2500D)
        .name("any-name").build();
  }

  Branch buildBranch() {
    return Branch.builder()
        .id(1L)
        .idFranchise(1L)
        .name("any-name")
        .build();
  }
}