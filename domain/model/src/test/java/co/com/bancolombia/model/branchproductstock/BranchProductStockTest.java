package co.com.bancolombia.model.branchproductstock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BranchProductStockTest {

  @InjectMocks
  BranchProductStock stock;

  public static final String NAME = "name";
  private static final Long ID_TEST = 1L;

  @BeforeEach
  void setup() {
    stock = new BranchProductStock();
    stock.setBranchId(ID_TEST);
    stock.setBranchName(NAME);
    stock.setProductId(ID_TEST);
    stock.setProductName(NAME);
    stock.setProductPrice(1D);
    stock.setStock(15L);
  }

  @Test
  void getBranchId() {
    Assertions.assertNotNull(stock.getBranchId());
  }

  @Test
  void getBranchName() {
    Assertions.assertNotNull(stock.getBranchName());
  }

  @Test
  void getProductId() {
    Assertions.assertNotNull(stock.getProductId());
  }

  @Test
  void getProductName() {
    Assertions.assertNotNull(stock.getBranchName());
  }

  @Test
  void getProductPrice() {
    Assertions.assertNotNull(stock.getProductPrice());
  }

  @Test
  void getStock() {
    Assertions.assertNotNull(stock.getStock());
  }
}