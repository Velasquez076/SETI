package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {

  private final BranchRepository branchRepository;

  public Mono<Branch> saveBranch(Branch branch) {
    branch.validateNameAndIdFranchise();
    return branchRepository.save(branch);
  }

  public Mono<Branch> updateBranch(Branch branch) {
    branch.validateNameAndId();
    return branchRepository.update(branch);
  }
}
