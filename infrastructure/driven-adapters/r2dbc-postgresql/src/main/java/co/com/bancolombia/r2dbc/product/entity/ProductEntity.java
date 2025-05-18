package co.com.bancolombia.r2dbc.product.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {

  @Id
  @Column(value = "id_product")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(value = "id_branch")
  private Long idBranch;
  private String name;
  private Double price;
  private Long stock;
}
