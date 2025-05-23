package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.branch.BranchReactiveRepository;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BranchRepositoryAdapter extends ReactiveAdapterOperations<
    Object/* change for domain model */,
    Object/* change for adapter model */,
    String,
    BranchReactiveRepository
    > {

  public BranchRepositoryAdapter(BranchReactiveRepository repository, ObjectMapper mapper) {
    /**
     *  Could be use mapper.mapBuilder if your domain model implement builder pattern
     *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
     *  Or using mapper.map with the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, Object.class/* change for domain model */));
  }

}
