package co.com.bancolombia.model.util;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.products.Product;

public class UtilMocks {

  public static Branch buildBranch(Long id, Long idFranchise, String name) {
    var branch = new Branch();
    branch.setId(id);
    branch.setIdFranchise(idFranchise);
    branch.setName(name);
    return branch;
  }

  public static Branch buildBranch(Long idFranchise, String name) {
    return new Branch(idFranchise, name);
  }

  public static Product buildProduct() {
    return new Product(1L, "name", 3000D, 15L);
  }
}
