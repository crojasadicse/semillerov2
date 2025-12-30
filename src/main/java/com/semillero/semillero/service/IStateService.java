package com.semillero.semillero.service;

import java.util.List;

import com.semillero.semillero.commons.ICrudCommons;
import com.semillero.semillero.models.StateEntity;

public interface IStateService extends ICrudCommons<StateEntity, Long> {

    List<StateEntity> getAllStates();





}
