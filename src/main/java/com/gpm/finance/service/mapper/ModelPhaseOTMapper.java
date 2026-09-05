package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.ModelPhaseOT;
import com.gpm.finance.service.dto.ModelPhaseOTDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelPhaseOT} and its DTO {@link ModelPhaseOTDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelPhaseOTMapper extends EntityMapper<ModelPhaseOTDTO, ModelPhaseOT> {}
