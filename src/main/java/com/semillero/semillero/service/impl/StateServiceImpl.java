package com.semillero.semillero.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.repository.IStateRepository;
import com.semillero.semillero.service.IStateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateServiceImpl implements IStateService {


    private final IStateRepository iStateRepository;

    @Override
    public StateEntity save(StateEntity entity) {
        return iStateRepository.save(entity);

    }

    @Override
    public StateEntity update(Long id, StateEntity entity) {

        StateEntity existing = iStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with id: " + id));

        BeanUtils.copyProperties(entity, existing, "idState", "createdAt");

        return iStateRepository.save(existing);        

    }

    @Override
    public StateEntity findById(Long id) {
        return iStateRepository.findById(id).orElse(null);
    }

    @Override
    public StateEntity delete(Long id) {
        StateEntity entity = findById(id);
        if (entity != null) {
            iStateRepository.deleteById(id);
            return entity;
        }
        return null;
    }

    @Override
    public List<StateEntity> getAllStates() {
        return iStateRepository.findAll();
    }



    public void printService() {
        System.out.println("State Service Implemented");
    }
       

}
