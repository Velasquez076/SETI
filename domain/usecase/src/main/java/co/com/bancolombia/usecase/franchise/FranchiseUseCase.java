package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

  private final FranchiseRepository franchiseRepository;

  public Mono<Franchise> save(Franchise franchise) {
    franchise.validateFields();
    return franchiseRepository.save(franchise);
  }

  public Mono<Franchise> update(Franchise franchise) {
    franchise.validateFields();
    return franchiseRepository.update(franchise);
  }
}
