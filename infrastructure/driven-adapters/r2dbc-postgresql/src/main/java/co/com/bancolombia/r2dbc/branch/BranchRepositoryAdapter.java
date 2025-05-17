package co.com.bancolombia.r2dbc.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.r2dbc.branch.entity.BranchEntity;
import co.com.bancolombia.r2dbc.exceptions.TechnicalException;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class BranchRepositoryAdapter extends ReactiveAdapterOperations<
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

  public Mono<Branch> save(Branch branch) {
    return super.save(branch)
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

//  public Mono<Branch> save(Branch branch) {
//    return Mono.just(branch)
//        .map(this::buildEntity)
//        .flatMap(repository::save)
//        .map(this::toDomain)
//        .onErrorResume(err -> {
//          log.error("{}, {}", err.getMessage(), err);
//          return Mono.error(new TechnicalException(err.getMessage()));
//        });
//  }
//
//  private Branch toDomain(BranchEntity entity) {
//    return Branch.builder()
//        .id(entity.getId())
//        .idFranchise(entity.getFranchise().getId())
//        .name(entity.getName())
//        .build();
//  }
//
//  private Franchise toFranchise(Long idFranchise) {
//    return Franchise.builder()
//        .id(idFranchise)
//        .build();
//  }
//
//  private BranchEntity buildEntity(Branch branch) {
//    var entity = new BranchEntity();
//    entity.setFranchise(toFranchise(branch.getIdFranchise()));
//    return entity;
//  }
}
