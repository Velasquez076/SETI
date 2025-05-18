package co.com.bancolombia.api.util;

import co.com.bancolombia.api.dto.WrapperResponse;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.exceptions.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
public abstract class ResponseErrorHandler {

  public Mono<ServerResponse> errorHandler(Throwable ex) {
    return switch (ex) {
      case TechnicalException technicalException ->
          WrapperResponse.buildResponse(technicalException.getMessage(), Messages.BAD_REQUEST.getCode());
      case BusinessException businessException ->
          WrapperResponse.buildResponse(businessException.getMessage(), Messages.BAD_REQUEST.getCode());
      default -> {
        log.error("{}, {}", ex.getMessage(), ex);
        yield WrapperResponse.buildResponse(ex.getMessage(), Messages.UNKNOW_ERROR.getCode());
      }
    };
  }
}
