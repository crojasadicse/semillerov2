package com.semillero.semillero.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.mappers.StateMapper;
import com.semillero.semillero.models.StateEntity;
import com.semillero.semillero.service.IStateService;



@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private IStateService iStateService;

    @Autowired
    private StateMapper stateMapper;

    @PostMapping
    public ResponseEntity<StateResponseDto> create(@RequestBody StateRequestDto dto) {


        StateEntity stateEntity = stateMapper.toEntity(dto);
        StateEntity savedState = iStateService.save(stateEntity);

        StateResponseDto responseDto = stateMapper.toDto(savedState);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
     
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponseDto> get(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(stateMapper.toDto(iStateService.findById(id)));

    }    



}
