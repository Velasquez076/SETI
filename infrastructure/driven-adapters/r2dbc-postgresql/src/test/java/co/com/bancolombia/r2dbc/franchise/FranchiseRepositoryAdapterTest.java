package co.com.bancolombia.r2dbc.franchise;

import static org.mockito.Mockito.when;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.r2dbc.franchise.entity.BranchEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FranchiseRepositoryAdapterTest {

  @Mock
  FranchiseReactiveRepository reactiveRepository;

  @Mock
  ObjectMapper mapper;

  @InjectMocks
  FranchiseRepositoryAdapter franchiseRepositoryAdapter;

  Franchise franchise = new Franchise(1L, "Test Franchise");
  BranchEntity entity = new BranchEntity(1L, "Test franchise Entity");
  RuntimeException error = new RuntimeException("Database error");

  private static final String DB_ERR_MSG = "Database error";

  @Test
  void saveFranchiseSuccessTest() {
    when(mapper.map(franchise, BranchEntity.class)).thenReturn(entity);
    when(reactiveRepository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Franchise.class)).thenReturn(franchise);

    var result = franchiseRepositoryAdapter.save(franchise);

    StepVerifier.create(result)
        .expectNext(franchise)
        .verifyComplete();
  }


  @Test
  void saveFranchiseTechnicalExceptionTest() {
    when(mapper.map(franchise, BranchEntity.class)).thenReturn(entity);
    when(reactiveRepository.save(entity)).thenReturn(Mono.error(error));

    var result = franchiseRepositoryAdapter.save(franchise);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }

  @Test
  void updateSuccessTest() {
    when(reactiveRepository.findById(1L)).thenReturn(Mono.just(entity));
    when(mapper.map(franchise, BranchEntity.class)).thenReturn(entity);
    when(reactiveRepository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.map(entity, Franchise.class)).thenReturn(franchise);

    franchise.setId(1L);
    franchise.setName("Bosi");
    var result = franchiseRepositoryAdapter.update(franchise);

    StepVerifier.create(result)
        .expectNext(franchise)
        .verifyComplete();
    Assertions.assertNotNull(result);
  }

  @Test
  void updateFranchiseTechnicalExceptionTest() {
    when(reactiveRepository.findById(1L)).thenReturn(Mono.error(error));

    franchise.setId(1L);
    var result = franchiseRepositoryAdapter.update(franchise);
    StepVerifier.create(result)
        .expectErrorMatches(throwable ->
            throwable instanceof TechnicalException &&
                throwable.getMessage().equals(DB_ERR_MSG))
        .verify();
  }
}