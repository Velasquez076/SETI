package co.com.bancolombia.api.franchise;

import static co.com.bancolombia.api.dto.WrapperResponse.buildResponse;
import static co.com.bancolombia.api.util.Messages.CREATE;
import static co.com.bancolombia.api.util.Messages.OK;

import co.com.bancolombia.api.dto.FranchiseDto;
import co.com.bancolombia.api.util.ResponseErrorHandler;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class FranchiseHandler extends ResponseErrorHandler {

  private final FranchiseUseCase franchiseUseCase;

  public Mono<ServerResponse> saveFranchise(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(FranchiseDto.class)
        .flatMap(franchiseDto ->
            franchiseUseCase.save(new Franchise(franchiseDto.id(), franchiseDto.name())))
        .doOnNext(f -> log.info("Saved franchise: id={}, name={}", f.getId(), f.getName()))
        .flatMap(franchise -> buildResponse(franchise, CREATE.getCode()))
        .doOnSuccess(sr -> log.info("Franchise saved successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }

  public Mono<ServerResponse> updateFranchise(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(FranchiseDto.class)
        .flatMap(franchiseDto -> franchiseUseCase.update(
            new Franchise(franchiseDto.id(), franchiseDto.name())))
        .doOnNext(f -> log.info("Updated franchise: id={}, name={}", f.getId(), f.getName()))
        .flatMap(franchise -> buildResponse(franchise, OK.getCode()))
        .doOnSuccess(sr -> log.info("Franchise updated successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }
}
