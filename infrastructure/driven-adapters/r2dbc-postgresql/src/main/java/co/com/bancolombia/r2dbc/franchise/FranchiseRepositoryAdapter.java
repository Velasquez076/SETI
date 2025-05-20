package co.com.bancolombia.r2dbc.franchise;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.r2dbc.franchise.entity.FranchiseEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Log4j2
@Repository
class FranchiseRepositoryAdapter extends ReactiveAdapterOperations<
    Franchise,
    FranchiseEntity,
    Long,
    FranchiseReactiveRepository
    > implements FranchiseRepository {

  public FranchiseRepositoryAdapter(FranchiseReactiveRepository repository, ObjectMapper mapper) {
    /**
     *  Could be use mapper.mapBuilder if your domain model implement builder pattern
     *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
     *  Or using mapper.map with the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, Franchise.class));
  }

  @Override
  public Mono<Franchise> save(Franchise franchise) {
    return super.save(franchise)
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }

  @Override
  public Mono<Franchise> update(Franchise franchise) {
    return findById(franchise.getId())
        .switchIfEmpty(Mono.error(new BusinessException("Franchise not found for the update!")))
        .flatMap(entity -> {
          entity.setName(franchise.getName());
          return super.save(franchise);
        })
        .doOnSuccess(success -> log.info("Updated to {}:", success.getName()))
        .onErrorResume(err -> {
          log.error("{}, {}", err.getMessage(), err);
          return Mono.error(new TechnicalException(err.getMessage()));
        });
  }
}
