package co.com.bancolombia.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class WrapperResponse<T> {

  private UUID uuid;
  private HttpStatusCode status;
  private T content;
}
