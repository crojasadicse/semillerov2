package com.semillero.semillero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.semillero.semillero.models.StateEntity;


public interface IStateRepository  extends JpaRepository<StateEntity, Long> {


    @Query("SELECT s FROM StateEntity s WHERE s.stateDescription LIKE %?1%")
    List<StateEntity> findByComment(String comment);

    

}
