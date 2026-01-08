package com.semillero.semillero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.semillero.semillero.models.StateEntity;


public interface IStateRepository  extends JpaRepository<StateEntity, Long> {


    @Query("SELECT s FROM StateEntity s WHERE s.stateDescription LIKE %?1%")
    List<StateEntity> findByComment(String comment);


    // No se recomienta para usar procedimientos almacenados desde el repositorio 
    // ya que no permite manejar los errores de la misma forma que con el EntityManager
    @Procedure(procedureName = "pkg_states.listar_states")
    List<StateEntity> getAllStateProcedure();

    
    




    

}
