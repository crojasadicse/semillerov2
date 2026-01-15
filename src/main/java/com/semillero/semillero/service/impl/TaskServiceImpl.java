package com.semillero.semillero.service.impl;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semillero.semillero.commons.FilterModel;
import com.semillero.semillero.commons.PaginationModel;
import com.semillero.semillero.dto.TaskRequestDto;
import com.semillero.semillero.dto.TaskResponseDto;
import com.semillero.semillero.exception.BadRequestException;
import com.semillero.semillero.exception.ResourceNotFoundException;
import com.semillero.semillero.mappers.TaskMapper;
import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.models.TaskEntity;
import com.semillero.semillero.repository.IStateRepository;
import com.semillero.semillero.repository.ITaskRepository;
import com.semillero.semillero.service.ITaskService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {


    private final ITaskRepository iTaskRepository;
    private final EntityManager entityManager;
    private final TaskMapper taskMapper;
    private final IStateRepository iStateRepository;


    @Override
    public TaskResponseDto save(TaskRequestDto dto) {

        Long stateId = dto.getIdState();
        StateEntity stateEntity = iStateRepository.findById(stateId)
                .orElseThrow(() -> new BadRequestException("State con id " + stateId + " no existe."));     



        TaskEntity entity = taskMapper.toEntity(dto);
        entity.setState(stateEntity);
        TaskEntity savedEntity = iTaskRepository.save(entity);
        return taskMapper.toDto(savedEntity);
       
    }

    @Override
    public TaskResponseDto update(Long id, TaskRequestDto dto) {

        TaskEntity taskEntity = taskMapper.toEntity(dto);

        TaskEntity existing = iTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Id de task no existe: " + id));

        BeanUtils.copyProperties(taskEntity, existing, "idTask", "createdAt");

        TaskEntity updatedEntity = iTaskRepository.save(existing);

        return taskMapper.toDto(updatedEntity);        

    }

    @Override
    public TaskResponseDto findById(Long id) {
        TaskEntity entity = iTaskRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("El Id de task no existe: " + id)
        );
        return taskMapper.toDto(entity);
        
    }

    @Override
    public TaskResponseDto delete(Long id) {
        TaskEntity existing = iTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        iTaskRepository.delete(existing);
        return taskMapper.toDto(existing);
    }

    @Override
    public List<TaskEntity> getAllTasks() {
        return iTaskRepository.findAll();
    }

    
    @Override
    public PageImpl<TaskResponseDto> getPagination(PaginationModel paginationModel) {
        Integer page = paginationModel.getPageNumber();
        Integer rowPage = paginationModel.getRowsPerPage();

        if(rowPage <=0 ){
            rowPage = 10;
        }     
        
        Pageable pageable =  PageRequest.of(page, rowPage);

        Long totalRegistros = 0L;     
        
        String sql = """
                    SELECT new com.semillero.semillero.dto.TaskResponseDto(
                    t.idTask, t.taskTitle, t.taskDescription, 
                    s.idState,s.stateDescription, t.userId, 
                    t.createdAt, t.updatedAt) FROM TaskEntity t join StateEntity s on t.state.idState = s.idState
                    """;

        String sqlCount = " SELECT COUNT(t.idTask) FROM TaskEntity t join StateEntity s on t.state.idState = s.idState";

        sql = StringWhereClause(paginationModel.getFilters(), sql);
        sqlCount = StringWhereClause(paginationModel.getFilters(), sqlCount);


        TypedQuery<TaskResponseDto> querySelect = entityManager.createQuery(sql, TaskResponseDto.class);
        querySelect.setFirstResult((int)pageable.getOffset());
        querySelect.setMaxResults(pageable.getPageSize());


        TypedQuery<Long> queryCount = entityManager.createQuery(sqlCount, Long.class);

        
        querySelect = ParamsWhereClause( querySelect, paginationModel.getFilters());
        queryCount = ParamsWhereClause( queryCount, paginationModel.getFilters());

        List<TaskResponseDto> results = querySelect.getResultList();
        totalRegistros = queryCount.getSingleResult();
        return new PageImpl<>(results, pageable, totalRegistros);

    }

    private String StringWhereClause( List<FilterModel> filters, String whereClause) {

        int i = 0;


        for (FilterModel filter : filters) {
            if(i > 0) {
                whereClause += " AND ";
            } else {
                whereClause += " WHERE ";
            }

            if (filter.getField().equals("taskTitle")) {
                whereClause += " upper(t.taskTitle) LIKE :paramTaskTitle ";
            }

           if (filter.getField().equals("taskDescription")) {
                whereClause += " upper(t.taskDescription) LIKE :paramTaskDescription ";
            }            

            i++;
        }

        return whereClause;
    }


    @SuppressWarnings("rawtypes")
    private TypedQuery ParamsWhereClause( TypedQuery querySelect, List<FilterModel> filters) {


        for (FilterModel filter : filters) {

            if (filter.getField().equals("taskTitle")) {
                querySelect.setParameter("paramTaskTitle", "%" + filter.getValue().toUpperCase() + "%");
            }
            if (filter.getField().equals("taskDescription")) {
                querySelect.setParameter("paramTaskDescription", "%" + filter.getValue().toUpperCase() + "%");
            }

        }

        return querySelect;
    }

  
}
