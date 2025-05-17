package co.com.bancolombia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchDto(Long id, @JsonProperty("id_franchise") Long idFranchise,
                        String name) {

}
