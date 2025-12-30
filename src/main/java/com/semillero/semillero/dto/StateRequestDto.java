package com.semillero.semillero.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StateRequestDto {
    
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 3, message = "La descripción debe tener al menos 3 caracteres")
    private String stateDescription;
    private String stateComment;    

}
