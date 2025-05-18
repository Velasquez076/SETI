package co.com.bancolombia.model.branch;

import co.com.bancolombia.model.exceptions.BusinessException;
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

  public Branch(Long idFranchise, String name) {
    this.idFranchise = idFranchise;
    this.name = name;
  }

  public void validateNameAndIdFranchise(Branch branch) {
    if (Objects.isNull(branch.getIdFranchise()) || Objects.isNull(branch.getName())) {
      throw new BusinessException("These fields id_franchise or name are required, cannot null!");
    }
  }

  public void validateNameAndId(Branch branch) {
    if (Objects.isNull(branch.getId()) || Objects.isNull(branch.getName())) {
      throw new BusinessException("The name or id is null!");
    }
  }
}
