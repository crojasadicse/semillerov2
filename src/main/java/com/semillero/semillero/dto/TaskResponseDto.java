package com.semillero.semillero.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponseDto {

    private Long idTask;
    private String taskTitle;
    private String taskDescription;
    private Long idState;
    private String stateDescription;
    private Long userId;
    private String createdAt;
    private String updatedAt;    

}
