package co.com.bancolombia.r2dbc.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.r2dbc.branch.entity.BranchEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
class BranchRepositoryAdapter extends ReactiveAdapterOperations<
    Branch,
    BranchEntity,
    Long,
    BranchReactiveRepository
    > implements BranchRepository {

  public BranchRepositoryAdapter(BranchReactiveRepository repository, ObjectMapper mapper) {
    /**
     *  Could be use mapper.mapBuilder if your domain model implement builder pattern
     *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
     *  Or using mapper.map with the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, Branch.class/* change for domain model */));
  }

  @Override
  public Mono<Branch> save(Branch branch) {
    return super.save(branch)
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Mono<Branch> update(Branch branch) {
    return repository.findById(branch.getId())
        .switchIfEmpty(Mono.error(new BusinessException("Branch not found for the update!")))
        .flatMap(entity -> {
          entity.setName(branch.getName());
          branch.setIdFranchise(entity.getIdFranchise());
          return super.save(branch);
        }).doOnSuccess(success -> log.info("Updated to {}:", success.getName()))
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Flux<Branch> findByFranchiseId(Long id) {
    return repository.findByIdFranchise(id)
        .switchIfEmpty(
            Mono.error(new BusinessException(String.format("Franchise with id '%s' not found", id))))
        .map(this::buildBranch)
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Flux.error(new TechnicalException(err.getMessage()));
        });
  }

  private Branch buildBranch(BranchEntity branchEntity) {
    return new Branch(branchEntity.getId(), branchEntity.getIdFranchise(),
        branchEntity.getName());
  }
}
