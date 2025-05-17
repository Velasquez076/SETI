package co.com.bancolombia.r2dbc.branch;

import co.com.bancolombia.r2dbc.branch.entity.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long>,
    ReactiveQueryByExampleExecutor<BranchEntity> {

}
