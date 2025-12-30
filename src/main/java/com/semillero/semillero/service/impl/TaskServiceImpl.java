package com.semillero.semillero.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;


import com.semillero.semillero.models.TaskEntity;
import com.semillero.semillero.repository.ITaskRepository;
import com.semillero.semillero.service.ITaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository iTaskServiceImpl;

    @Override
    public TaskEntity save(TaskEntity entity) {
       return iTaskServiceImpl.save(entity);
    }

    @Override
    public TaskEntity update(Long id, TaskEntity entity) {
        TaskEntity existing = iTaskServiceImpl.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        BeanUtils.copyProperties(entity, existing, "idState", "createdAt");

        return iTaskServiceImpl.save(existing);  

    }

    @Override
    public TaskEntity findById(Long id) {
        return iTaskServiceImpl.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
    }

    @Override
    public TaskEntity delete(Long id) {
        TaskEntity existing = iTaskServiceImpl.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        iTaskServiceImpl.delete(existing);
        return existing;
    }

    @Override
    public List<TaskEntity> getAllTasks() {
        return iTaskServiceImpl.findAll();
    }

}
