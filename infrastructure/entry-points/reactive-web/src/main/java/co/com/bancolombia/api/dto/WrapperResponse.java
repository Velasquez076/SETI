package co.com.bancolombia.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperResponse<T> {

  private UUID uuid;
  private HttpStatusCode status;
  private T content;

  public static <T> Mono<ServerResponse> buildResponse(T object, int code) {
    var data = new WrapperResponse<>(UUID.randomUUID(), HttpStatusCode.valueOf(code),
        object);
    return ServerResponse.status(code).bodyValue(data);
  }
}
