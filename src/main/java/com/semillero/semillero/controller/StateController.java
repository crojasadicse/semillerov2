package com.semillero.semillero.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;
import com.semillero.semillero.service.IStateService;



@RestController
@RequestMapping("/states")
public class StateController {

    
    @Autowired
    private IStateService iStateService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StateResponseDto> create(@RequestBody StateRequestDto dto) {

        StateResponseDto responseDto = iStateService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
     
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @GetMapping("/{id}")
    public ResponseEntity<StateResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.findById(id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StateResponseDto> update(@PathVariable Long id, @RequestBody StateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.update(id, dto));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<StateResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
    @GetMapping
    public ResponseEntity<Iterable<StateResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.getAllStates());
    }    


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
    @GetMapping("/jpa")
    public ResponseEntity<List<StateResponseDto>> getAllJpa() {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.getAllStatesJpa());
    }    


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
    @GetMapping("/procedure")
    public ResponseEntity<List<StateResponseDto>> getAllFromProcedure() {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.getAllFromProcedure());
    }      
    
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
    @GetMapping("/procedure-repository")
    public ResponseEntity<List<StateResponseDto>> getAllFromProcedureRepository() {
        return ResponseEntity.status(HttpStatus.OK).body(iStateService.getAllFromProcedureRepository());
    }      
}
