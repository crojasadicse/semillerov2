package com.semillero.semillero.service.impl;


import java.util.List;

import org.antlr.v4.runtime.misc.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.semillero.semillero.commons.FilterModel;
import com.semillero.semillero.commons.PaginationModel;
import com.semillero.semillero.commons.SortModel;
import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.exception.BadRequestException;
import com.semillero.semillero.exception.ResourceNotFoundException;
import com.semillero.semillero.mappers.StateMapper;
import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.repository.IStateRepository;
import com.semillero.semillero.service.IStateService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
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
                .orElseThrow(() -> new ResourceNotFoundException("El Id de state no existe: " + id));

        BeanUtils.copyProperties(stateEntity, existing, "idState", "createdAt");

        StateEntity updatedEntity = iStateRepository.save(existing);

        return stateMapper.toDto(updatedEntity);

    }

    @Override
    public StateResponseDto findById(Long id) {
        StateEntity entity = iStateRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("El Id de state no existe: " + id)
        );
        return stateMapper.toDto(entity);
    }

    @Override
    public StateResponseDto delete(Long id) {
        StateEntity entity = iStateRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("State not found with id: " + id)
        );
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

        try {
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
            
        } catch (Exception e) {
            throw new BadRequestException("Error al ejecutar la consulta JPA: " + e.getMessage());
        }


    }

    @Override
    public List<StateResponseDto> getAllFromProcedure() {

        try {
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("pkg_states.listar_states",StateEntity.class);
            query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
    
            query.execute();
            List<StateEntity> entities = query.getResultList();
            //List<Object[]> result = query.getResultList();   
            return stateMapper.toDtoList(entities);
            
            //return stateMapper.toDtoListFromProcedure(result);
            
        } catch (Exception e) {
            throw new BadRequestException("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
        }


    }

    @Override
    public List<StateResponseDto> getAllFromProcedureRepository() {
        try {
            List<StateEntity> entities = iStateRepository.getAllStateProcedure();
            return stateMapper.toDtoList(entities);
        } catch (Exception e) {
            throw new BadRequestException("Error al ejecutar el procedimiento almacenado desde el repositorio: " );
        }
    }

    @Override
    public PageImpl<StateResponseDto> getPagination(PaginationModel paginationModel) {

        Integer page = paginationModel.getPageNumber();
        Integer rowPage = paginationModel.getRowsPerPage();

        if(rowPage <=0 ){
            rowPage = 10;
        }

        Pageable pageable =  PageRequest.of(page, rowPage);

        Long totalRegistros = 0L;

        String sql = "SELECT new com.semillero.semillero.dto.StateResponseDto " 
        + "(s.idState, s.stateDescription, s.stateComment, s.createdAt, s.updatedAt) FROM StateEntity s";

   


        List<SortModel> sorts = paginationModel.getSorts();
        List<FilterModel> filters = paginationModel.getFilters();

        for (SortModel sort : sorts) {
            sql += " ORDER BY s.idState + "  + sort.getDirection();

            if(sort.getColName().equals("stateDescription")){
                sql += " , s.stateDescription "  + sort.getDirection();
            }
        }
        String sqlCount = " SELECT COUNT(s.idState) FROM StateEntity s";

        TypedQuery<StateResponseDto> querySelect = entityManager.createQuery(sql,StateResponseDto.class);

        querySelect.setFirstResult((int)pageable.getOffset());
        querySelect.setMaxResults(pageable.getPageSize());      

        StringBuilder whereClause = new StringBuilder();
        int i = 1;
        



        TypedQuery<Long> queryCount = entityManager.createQuery(sqlCount, Long.class);

        List<StateResponseDto> results = querySelect.getResultList();
        totalRegistros = queryCount.getSingleResult();


        return new PageImpl<>(results, pageable, totalRegistros);


    }




       

}
