package co.com.bancolombia.model.products;

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
public class Product {

  private Long id;
  private Long idBranch;
  private String name;
  private Double price;
  private Long stock;

  public Product(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Product(Long id, Long stock) {
    this.id = id;
    this.stock = stock;
  }

  public Product(Long id) {
    this.id = id;
  }

  public Product(Long idBranch, String name, Double price, Long stock) {
    this.idBranch = idBranch;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public void validations(Product product) {
    if (product.getPrice() <= 0) {
      throw new BusinessException("Price must be positive");
    }
    validateName(product.getName());
    validateStock(product.getStock());
  }

  public void validateId(Long id) {
    if (Objects.isNull(id)) {
      throw new BusinessException("Id cannot be null");
    }
  }

  public void validateStock(Long stock) {
    if (stock < 0) {
      throw new BusinessException("Stock cannot be negative");
    }
  }

  public void validateName(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new BusinessException("Product name cannot be empty");
    }
  }
}
