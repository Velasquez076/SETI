package co.com.bancolombia.usecase.branch;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.usecase.UtilMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

  @Mock
  BranchRepository branchRepository;

  @InjectMocks
  BranchUseCase branchUseCase;

  @Test
  void saveBranchSuccessTest() {
    var branch = UtilMock.buildBranch(1L, "name");

    doReturn(Mono.just(branch)).when(branchRepository).save(branch);
    var result = branchUseCase.saveBranch(branch);

    StepVerifier.create(result)
        .expectNext(branch)
        .verifyComplete();

    verify(branchRepository).save(branch);
  }

  @Test
  void updateBranchSuccessTest() {
    var branch = UtilMock.buildBranch(1L, 1L, "name");

    doReturn(Mono.just(branch)).when(branchRepository).update(branch);
    var result = branchUseCase.updateBranch(branch);

    StepVerifier.create(result)
        .expectNext(branch)
        .verifyComplete();

    verify(branchRepository).update(branch);
    verify(branchRepository, never()).save(branch);
  }
}