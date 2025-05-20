package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.exceptions.BusinessException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Franchise {

  private Long id;
  private String name;

  public void validateName() {
    if (Objects.isNull(this.getName())) {
      throw new BusinessException("Franchise name is null, is required!");
    }
  }

  public void validateId() {
    if (Objects.isNull(this.getId())) {
      throw new BusinessException("Franchise id is null, is required!");
    }
  }
}
