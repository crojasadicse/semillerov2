package com.semillero.semillero.mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.models.StateEntity;

@Component
public class StateMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public  StateEntity toEntity(StateRequestDto dto) {

        return StateEntity.builder()
                .stateDescription(dto.getStateDescription())
                .stateComment(dto.getStateComment())
                .build();
    }  

    public  StateResponseDto toDto(StateEntity entity) {
        return StateResponseDto.builder()
                .idState(entity.getIdState())
                .stateDescription(entity.getStateDescription())
                .stateComment(entity.getStateComment())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().format(FORMATTER) : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().format(FORMATTER) : null)
                .build();
    } 
    
    public  List<StateResponseDto> toDtoList(List<StateEntity> entities) {

        // List<StateResponseDto> dtoList = new ArrayList<>();
        // for (StateEntity stateEntity : entities) {
        //     StateResponseDto dto = toDto(stateEntity);
        //     dtoList.add(dto);
        // }

        // return dtoList;

        return entities.stream()
            .map(this::toDto)
            .toList();

    }  
    
    
    public List<StateEntity> toEntityList(List<StateRequestDto> dtoList) {

        return dtoList.stream()
            .map(this::toEntity)
            .toList();

    }    


}
