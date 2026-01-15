package com.semillero.semillero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    private String taskTitle;
    private String taskDescription;
    private Long idState;
    private Long userId;    

}
