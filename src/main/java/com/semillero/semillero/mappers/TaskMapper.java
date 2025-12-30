package com.semillero.semillero.mappers;


import java.time.format.DateTimeFormatter;
import java.util.List;

import com.semillero.semillero.dto.TaskRequestDto;
import com.semillero.semillero.dto.TaskResponseDto;
import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.models.TaskEntity;

public class TaskMapper {



    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public TaskEntity toEntity(TaskRequestDto dto) {

        Long idState = dto.getIdState();
        StateEntity stateEntity = StateEntity.builder()
                .idState(idState)
                .build();

        return TaskEntity.builder()
                .taskTitle(dto.getTaskTitle())
                .taskDescription(dto.getTaskDescription())
                .state( stateEntity)
                .userId(dto.getUserId())
                .build();
    }

    public TaskResponseDto toDto(TaskEntity entity) {
        return TaskResponseDto.builder()
                .idTask(entity.getIdTask())
                .taskTitle(entity.getTaskTitle())
                .taskDescription(entity.getTaskDescription())
                .idState(entity.getState() != null ? entity.getState().getIdState() : null)
                .stateDescription(entity.getState() != null ? entity.getState().getStateDescription() : null)
                .userId(entity.getUserId())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().format(FORMATTER) : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().format(FORMATTER) : null)
                .build();
    }       


    public  List<TaskResponseDto> toDtoList(List<TaskEntity> entities) {

        return entities.stream()
            .map(this::toDto)
            .toList();
        

    } 
    
    public List<TaskEntity> toEntityList(List<TaskRequestDto> dtoList) {

        return dtoList.stream()
            .map(this::toEntity)
            .toList();

    }    

}
