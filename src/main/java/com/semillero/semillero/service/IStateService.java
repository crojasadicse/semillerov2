package com.semillero.semillero.service;

import java.util.List;


import com.semillero.semillero.commons.ICrudCommonsDto;
import com.semillero.semillero.dto.StateRequestDto;
import com.semillero.semillero.dto.StateResponseDto;


public interface IStateService extends ICrudCommonsDto<StateRequestDto, StateResponseDto, Long> {

    List<StateResponseDto> getAllStates();
    
    List<StateResponseDto> getAllStatesJpa();

    List<StateResponseDto> getAllFromProcedure();

    List<StateResponseDto> getAllFromProcedureRepository();





}
