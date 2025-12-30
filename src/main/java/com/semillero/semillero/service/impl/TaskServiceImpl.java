package com.semillero.semillero.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.semillero.semillero.models.TaskEntity;
import com.semillero.semillero.repository.ITaskRepository;
import com.semillero.semillero.service.ITaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository iTaskRepository;

    @Override
    public TaskEntity save(TaskEntity entity) {
       return iTaskRepository.save(entity);
    }

    @Override
    public TaskEntity update(Long id, TaskEntity entity) {
        TaskEntity existing = iTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        BeanUtils.copyProperties(entity, existing, "idState", "createdAt");

        return iTaskRepository.save(existing);  

    }

    @Override
    public TaskEntity findById(Long id) {
        return iTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
    }

    @Override
    public TaskEntity delete(Long id) {
        TaskEntity existing = iTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        iTaskRepository.delete(existing);
        return existing;
    }

    @Override
    public List<TaskEntity> getAllTasks() {
        return iTaskRepository.findAll();
    }

}
