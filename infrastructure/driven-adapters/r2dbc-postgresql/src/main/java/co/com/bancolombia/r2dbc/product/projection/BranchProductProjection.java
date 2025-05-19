package co.com.bancolombia.r2dbc.product.projection;

public interface BranchProductProjection {

  Long getId();

  String getName();

  Double getPrice();

  Long getStock();

  Long getIdBranch();
}
