package com.semillero.semillero.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.mappers.StateMapper;
import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.repository.IStateRepository;
import com.semillero.semillero.service.IStateService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements IStateService {


    private final IStateRepository iStateRepository;
    private final StateMapper stateMapper;    

    private final EntityManager entityManager;


    @Override
    public StateResponseDto save(StateRequestDto dto) {

        StateEntity stateEntity = stateMapper.toEntity(dto);

        StateEntity savedEntity = iStateRepository.save(stateEntity);

        return stateMapper.toDto(savedEntity);

    }

    @Override
    public StateResponseDto update(Long id, StateRequestDto dto) {

        StateEntity stateEntity = stateMapper.toEntity(dto);

        StateEntity existing = iStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with id: " + id));

        BeanUtils.copyProperties(stateEntity, existing, "idState", "createdAt");

        StateEntity updatedEntity = iStateRepository.save(existing);

        return stateMapper.toDto(updatedEntity);

    }

    @Override
    public StateResponseDto findById(Long id) {
        StateEntity entity = iStateRepository.findById(id).orElse(null);
        return stateMapper.toDto(entity);
    }

    @Override
    public StateResponseDto delete(Long id) {
        StateEntity entity = iStateRepository.findById(id).orElse(null);
        if (entity != null) {
            iStateRepository.deleteById(id);
            return stateMapper.toDto(entity);
        }
        return null;
    }

    @Override
    public List<StateResponseDto> getAllStates() {
        List<StateEntity> entities = iStateRepository.findAll();
        return stateMapper.toDtoList(entities);
    }

    @Override
    public List<StateResponseDto> getAllStatesJpa() {

        String sqlJpqlDto = """
                SELECT new com.semillero.semillero.dto.
                StateResponseDto(s.idState, s.stateDescription, s.stateComment, 
                s.createdAt, 
                s.updatedAt) 
                FROM StateEntity s 
                """;

                sqlJpqlDto += "WHERE s.idState = 5 ";
                sqlJpqlDto += "ORDER BY s.idState ASC";

        Query queryJpql = entityManager.createQuery(sqlJpqlDto, StateResponseDto.class);
        List<StateResponseDto> entities = queryJpql.getResultList();

         return entities;

    }




       

}
