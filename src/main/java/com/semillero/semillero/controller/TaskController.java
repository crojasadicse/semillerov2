package com.semillero.semillero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.semillero.commons.PaginationModel;
import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.dto.TaskRequestDto;
import com.semillero.semillero.dto.TaskResponseDto;
import com.semillero.semillero.service.ITaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    private ITaskService iTaskService;

    public TaskController(ITaskService iTaskService) {
        this.iTaskService = iTaskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@RequestBody TaskRequestDto dto) {

        TaskResponseDto responseDto = iTaskService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
     
    }    

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iTaskService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @RequestBody TaskRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(iTaskService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iTaskService.delete(id));
    }    
        
    @PostMapping("/pagination")
    public ResponseEntity<Iterable<TaskResponseDto>> pagination(@RequestBody PaginationModel paginationModel) {

        var page = iTaskService.getPagination(paginationModel);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }        





}
