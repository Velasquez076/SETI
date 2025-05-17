package co.com.bancolombia.api.franchise;

import co.com.bancolombia.api.dto.FranchiseDto;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class FranchiseHandler {

  private final FranchiseUseCase franchiseUseCase;

  public Mono<ServerResponse> saveFranchise(ServerRequest serverRequest) {
    log.info("Init save franchise...");
    return serverRequest.bodyToMono(FranchiseDto.class)
        .map(franchiseDto -> new Franchise(franchiseDto.id(), franchiseDto.name()))
        .flatMap(franchiseUseCase::save)
        .doOnNext(f -> log.info("Saved franchise: id={}, name={}", f.getId(), f.getName()))
        .map(saved -> new FranchiseDto(saved.getId(), saved.getName(), UUID.randomUUID()))
        .flatMap(dto -> ServerResponse.ok().bodyValue(dto))
        .doOnSuccess(sr -> log.info("Franchise saved successfully! {}", sr.statusCode()));
  }

  public Mono<ServerResponse> updateFranchise(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(FranchiseDto.class)
        .map(franchiseDto -> new Franchise(franchiseDto.id(), franchiseDto.name()))
        .flatMap(franchiseUseCase::update)
        .map(saved -> new FranchiseDto(saved.getId(), saved.getName(), UUID.randomUUID()))
        .flatMap(dto -> ServerResponse.ok().bodyValue(dto))
        .doOnSuccess(sr -> log.info("Franchise updated successfully! {}", sr.statusCode()));
  }
}
