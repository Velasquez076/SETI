package co.com.bancolombia.model.branch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.util.UtilMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BranchTest {

  @InjectMocks
  Branch branch;

  @Test
  void validateNameAndIdFranchiseSuccessTest() {
    branch = UtilMocks.buildBranch(1L, getName());
    branch.validateNameAndIdFranchise();
    assertNotNull(branch.getIdFranchise());
  }

  @Test
  void validateNameAndIdFranchiseIdNullTest() {
    branch = new Branch(null, getName());
    final var exception = assertThrows(BusinessException.class, () ->
        branch.validateNameAndIdFranchise());
    assertNotNull(exception.getMessage());
  }

  @Test
  void validateNameAndIdFranchiseNameNullTest() {
    branch = new Branch(1L, null);
    final var exception = assertThrows(BusinessException.class, () ->
        branch.validateNameAndIdFranchise());
    assertNotNull(exception.getMessage());
  }

  @Test
  void validateNameAndIdSuccessTest() {
    branch = UtilMocks.buildBranch(1L, 1L, getName());
    branch.validateNameAndId();
    assertNotNull(branch.getName());
  }

  @Test
  void validateNameIsNullTest() {
    branch = UtilMocks.buildBranch(null, null, getName());
    final var exception = assertThrows(BusinessException.class, () ->
        branch.validateNameAndId());
    assertNotNull(exception.getMessage());
  }

  @Test
  void validateIdIsNullTest() {
    branch = UtilMocks.buildBranch(1L, 1L, null);
    final var exception = assertThrows(BusinessException.class, () ->
        branch.validateNameAndId());
    assertNotNull(exception.getMessage());
  }

  private static String getName() {
    return "any-name";
  }
}