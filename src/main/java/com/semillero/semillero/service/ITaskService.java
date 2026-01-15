package com.semillero.semillero.service;

import java.util.List;

import com.semillero.semillero.commons.ICrudCommons;
import com.semillero.semillero.commons.ICrudCommonsDto;
import com.semillero.semillero.commons.IPaginationCommons;
import com.semillero.semillero.dto.TaskRequestDto;
import com.semillero.semillero.dto.TaskResponseDto;
import com.semillero.semillero.models.TaskEntity;

public interface ITaskService extends ICrudCommonsDto<TaskRequestDto, TaskResponseDto, Long>, IPaginationCommons<TaskResponseDto> {

    List<TaskEntity> getAllTasks();

}
