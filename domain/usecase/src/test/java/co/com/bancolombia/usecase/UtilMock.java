package co.com.bancolombia.usecase;

import co.com.bancolombia.model.branch.Branch;

public class UtilMock {

  public static Branch buildBranch(Long idFranchise, String name) {
    return new Branch(idFranchise, name);
  }

  public static Branch buildBranch(Long id, Long idFranchise, String name) {
    return new Branch(id, idFranchise, name);
  }
}
