package com.semillero.semillero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateResponseDto {

    private Long idState;
    private String stateDescription;
    private String stateComment;
    private String updatedAt;
    private String createdAt;


}
