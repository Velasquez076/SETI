package co.com.bancolombia.model.branchproductstock;

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
public class BranchProductStock {

  private Long branchId;
  private Long productId;
  private String productName;
  private Double productPrice;
  private Long stock;
}
