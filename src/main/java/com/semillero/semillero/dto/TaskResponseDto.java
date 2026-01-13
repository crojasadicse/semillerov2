package com.semillero.semillero.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private Long idTask;
    private String taskTitle;
    private String taskDescription;
    private Long idState;
    private String stateDescription;
    private Long userId;
    private String createdAt;
    private String updatedAt;  
    
    
    public TaskResponseDto(Long idTask, String taskTitle, String taskDescription,
            Long idState, String stateDescription, Long userId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idTask = idTask;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.idState = idState;
        this.stateDescription = stateDescription;
        this.userId = userId;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.updatedAt = updatedAt != null ? updatedAt.toString() : null;
    }

}
