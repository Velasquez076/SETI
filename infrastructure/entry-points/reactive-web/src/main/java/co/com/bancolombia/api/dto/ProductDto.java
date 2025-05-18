package co.com.bancolombia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDto(Long id, @JsonProperty("id_branch") Long idBranch, String name,
                         Double price, Long stock) {

}
