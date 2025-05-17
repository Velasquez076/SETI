package co.com.bancolombia.api.branch;

import co.com.bancolombia.api.dto.BranchDto;
import co.com.bancolombia.api.dto.WrapperResponse;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class BranchHandler {

  private final BranchUseCase branchUseCase;

  public Mono<ServerResponse> saveBranch(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(BranchDto.class)
        .map(dto -> new Branch(dto.id(), dto.idFranchise(), dto.name()))
        .flatMap(branchUseCase::saveBranch)
        .doOnNext(f -> log.info("Saved Branch: id={}, name={}", f.getId(), f.getName()))
        .map(this::buildWrapper)
        .flatMap(dto -> ServerResponse.ok().bodyValue(dto))
        .doOnSuccess(sr -> log.info("Branch saved successfully! {}", sr.statusCode()));
  }

  private WrapperResponse<BranchDto> buildWrapper(Branch branch) {
    return new WrapperResponse<>(UUID.randomUUID(), HttpStatusCode.valueOf(201),
        new BranchDto(branch.getId(), branch.getIdFranchise(), branch.getName()));
  }
}
