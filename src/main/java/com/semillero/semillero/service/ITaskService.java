package com.semillero.semillero.service;

import java.util.List;

import com.semillero.semillero.commons.ICrudCommons;
import com.semillero.semillero.models.TaskEntity;

public interface ITaskService extends ICrudCommons<TaskEntity, Long> {

    List<TaskEntity> getAllTasks();

}
