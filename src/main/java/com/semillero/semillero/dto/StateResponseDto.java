package com.semillero.semillero.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StateResponseDto {

    private Long idState;
    private String stateDescription;
    private String stateComment;
    private String updatedAt;
    private String createdAt;


}
