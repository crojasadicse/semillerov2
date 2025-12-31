package com.semillero.semillero.dto;

import java.time.LocalDateTime;

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

    public StateResponseDto(Long idState, String stateDescription, String stateComment, 
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.idState = idState;
        this.stateDescription = stateDescription;
        this.stateComment = stateComment;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toString() : null;
    }


}
