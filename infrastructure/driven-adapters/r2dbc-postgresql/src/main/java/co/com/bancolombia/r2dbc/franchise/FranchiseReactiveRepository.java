package co.com.bancolombia.r2dbc.franchise;

import co.com.bancolombia.r2dbc.franchise.entity.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long>,
    ReactiveQueryByExampleExecutor<BranchEntity> {

}
