package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.franchise.exceptions.BusinessException;
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

  public Franchise validateFranchiseExist(String name) {
    if (name.equalsIgnoreCase(this.name)) {
      throw new BusinessException(
          String.format("This franchise name '%s' is already exist!", name));
    }
    return new Franchise(null, name);
  }

  public void validateNameNotNull(String name) {
    if (Objects.isNull(name)) {
      throw new BusinessException("Franchise name is required!");
    }
  }

  public void validateIdNotNull(Long id) {
    if (Objects.isNull(id)) {
      throw new BusinessException("Franchise id is required!");
    }
  }
}
