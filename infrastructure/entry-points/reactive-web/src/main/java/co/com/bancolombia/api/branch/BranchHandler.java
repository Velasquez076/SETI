package co.com.bancolombia.api.branch;

import static co.com.bancolombia.api.dto.WrapperResponse.buildResponse;

import co.com.bancolombia.api.dto.BranchDto;
import co.com.bancolombia.api.util.Messages;
import co.com.bancolombia.api.util.ResponseErrorHandler;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class BranchHandler extends ResponseErrorHandler {

  private final BranchUseCase branchUseCase;

  public Mono<ServerResponse> saveBranch(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(BranchDto.class)
        .flatMap(branchDto -> branchUseCase.saveBranch(
            new Branch(branchDto.getIdFranchise(), branchDto.getName())))
        .doOnNext(f -> log.info("Saved Branch: id={}, name={}", f.getId(), f.getName()))
        .flatMap(branch -> buildResponse(branch, Messages.CREATE.getCode()))
        .doOnSuccess(sr -> log.info("Branch saved successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }

  public Mono<ServerResponse> updateBranch(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(BranchDto.class)
        .flatMap(branchDto -> branchUseCase.updateBranch(
            new Branch(branchDto.getId(), branchDto.getIdFranchise(), branchDto.getName())))
        .doOnNext(f -> log.info("Updated Branch: id={}, name={}", f.getId(), f.getName()))
        .flatMap(branch -> buildResponse(branch, Messages.OK.getCode()))
        .doOnSuccess(sr -> log.info("Branch updated successfully! {}", sr.statusCode()))
        .onErrorResume(this::errorHandler);
  }
}
