package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.FraisDeMission;
import com.gpm.finance.service.dto.FraisDeMissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FraisDeMission} and its DTO {@link FraisDeMissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface FraisDeMissionMapper extends EntityMapper<FraisDeMissionDTO, FraisDeMission> {}
