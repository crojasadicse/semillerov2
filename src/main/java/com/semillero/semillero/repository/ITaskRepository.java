package com.semillero.semillero.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.semillero.semillero.models.TaskEntity;

public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {


    

}
