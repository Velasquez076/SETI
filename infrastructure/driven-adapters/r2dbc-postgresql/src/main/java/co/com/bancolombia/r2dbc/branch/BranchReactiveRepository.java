package co.com.bancolombia.r2dbc.branch;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchReactiveRepository extends ReactiveCrudRepository<Object, String>,
    ReactiveQueryByExampleExecutor<Object> {

}
