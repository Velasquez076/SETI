package co.com.bancolombia.api.branch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import co.com.bancolombia.api.dto.BranchDto;
import co.com.bancolombia.api.dto.WrapperResponse;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(BranchHandler.class)
@ContextConfiguration(classes = {BranchRouterRest.class, BranchHandler.class,
    BranchUseCase.class})
class BranchRouterRestTest {

  @MockitoBean
  BranchUseCase branchUseCase;

  @MockitoBean
  BranchRepository branchRepository;

  @Autowired
  WebTestClient webTestClient;

  @Test
  void branchRouterFunctionTest_Post() {
    Long branchId = 1L;
    String branchName = "name-br";
    Branch branchSaved = new Branch(branchId, branchName);
    BranchDto requestBody = new BranchDto(null, 1L, branchName);

    Mockito.when(branchUseCase.saveBranch(Mockito.any(Branch.class)))
        .thenReturn(Mono.just(branchSaved));
    Mockito.when(branchRepository.save(Mockito.any(Branch.class)))
        .thenReturn(Mono.just(branchSaved));

    var response = webTestClient.post()
        .uri("/api/branch")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(requestBody), BranchDto.class)
        .exchange();

    Assertions.assertNotNull(response);
    response.expectStatus().isCreated();
    var wrapper =
        response.expectBody(new ParameterizedTypeReference<WrapperResponse<BranchDto>>() {
        });
    Assertions.assertNotNull(wrapper);
    var body = wrapper.returnResult().getResponseBody().getContent();
    Assertions.assertNotNull(body);
    assertEquals(requestBody.getName(), body.getName());
  }
}