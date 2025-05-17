package co.com.bancolombia.model.branch;

import co.com.bancolombia.model.franchise.exceptions.BusinessException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {

  private Long id;
  private Long idFranchise;
  private String name;

  public void validateNameAndIdFranchise(Branch branch) {
    if (Objects.isNull(branch.idFranchise) || Objects.isNull(branch.getName())) {
      throw new BusinessException("These fields id_franchise or name are required!");
    }
  }
}
