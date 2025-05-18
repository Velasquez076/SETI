package co.com.bancolombia.r2dbc.branch;

import co.com.bancolombia.r2dbc.branch.entity.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long>,
    ReactiveQueryByExampleExecutor<BranchEntity> {

  Flux<BranchEntity> findByIdFranchise(Long franchiseId);
}
