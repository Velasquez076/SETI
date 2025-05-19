package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.exceptions.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FranchiseTest {

  @InjectMocks
  Franchise franchise;

  @Test
  void validateFieldsIdSuccessTest() {
    franchise = new Franchise(1L, "name");
    franchise.validateName();
  }

  @Test
  void validateFieldsNameSuccessTest() {
    franchise = new Franchise(1L, "name");
    franchise.validateName();
  }

  @Test
  void validateFieldsNameNullTest() {
    franchise = new Franchise(1L, null);
    Assertions.assertThrows(BusinessException.class, () ->
        franchise.validateName()
    );
  }

  @Test
  void validateFieldsIdNullTest() {
    franchise = new Franchise(null, "name");
    Assertions.assertThrows(BusinessException.class, () ->
        franchise.validateId()
    );
  }
}