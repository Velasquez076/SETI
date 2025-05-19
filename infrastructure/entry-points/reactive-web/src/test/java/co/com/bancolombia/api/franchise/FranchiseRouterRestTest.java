package co.com.bancolombia.api.franchise;

import co.com.bancolombia.api.dto.FranchiseDto;
import co.com.bancolombia.api.dto.WrapperResponse;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(FranchiseHandler.class)
@ContextConfiguration(classes = {FranchiseRouterRest.class, FranchiseHandler.class,
    FranchiseUseCase.class})
class FranchiseRouterRestTest {

  @MockitoSpyBean
  FranchiseUseCase franchiseUseCase;

  @MockitoBean
  FranchiseRepository franchiseRepository;

  @Autowired
  WebTestClient webTestClient;

  //@Test
  void franchiseRouterFunctionTest_Post() {

    Long franchiseId = 1L;
    String franchiseName = "name-franchise";
    Franchise franchiseSaved = new Franchise(franchiseId, franchiseName);
    FranchiseDto requestBody = new FranchiseDto(null, franchiseName);

    Mockito.doReturn(Mono.just(franchiseSaved))
        .when(franchiseUseCase).save(Mockito.any(Franchise.class));
//    Mockito.doReturn(Mono.just(franchiseSaved)).when(franchiseRepository).save(franchiseSaved);

    var response = webTestClient.post()
        .uri("/api/franchise")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(requestBody), FranchiseDto.class)
        .exchange();

    Assertions.assertNotNull(response);
    var wrapper =
        response.expectBody(new ParameterizedTypeReference<WrapperResponse<FranchiseDto>>() {
        });
    Assertions.assertNotNull(wrapper);
  }
}