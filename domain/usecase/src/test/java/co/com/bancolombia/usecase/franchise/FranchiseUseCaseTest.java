package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {

  @Mock
  FranchiseRepository franchiseRepository;

  @InjectMocks
  FranchiseUseCase franchiseUseCase;

  @Test
  void saveTest() {
    var franchise = new Franchise(1L, "name");
    Mockito.doReturn(Mono.just(franchise))
        .when(franchiseRepository).save(franchise);

    var result = franchiseUseCase.save(franchise);

    StepVerifier.create(result)
        .expectNext(franchise)
        .verifyComplete();

    Mockito.verify(franchiseRepository, Mockito.times(1))
        .save(franchise);
  }

  @Test
  void updateTest() {
    var franchise = new Franchise(1L, "name");
    Mockito.doReturn(Mono.just(franchise))
        .when(franchiseRepository).update(franchise);

    var result = franchiseUseCase.update(franchise);

    StepVerifier.create(result)
        .expectNext(franchise)
        .verifyComplete();

    Mockito.verify(franchiseRepository, Mockito.times(1))
        .update(franchise);
  }
}