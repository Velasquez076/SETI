package co.com.bancolombia.r2dbc.branch.entity;

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
@Table(name = "branches")
public class BranchEntity {

  @Id
  @Column(value = "id_branch")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(value = "id_franchise")
  private Long idFranchise;
  private String name;
}
