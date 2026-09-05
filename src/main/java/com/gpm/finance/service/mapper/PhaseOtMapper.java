package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.PhaseOt;
import com.gpm.finance.service.dto.PhaseOtDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhaseOt} and its DTO {@link PhaseOtDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhaseOtMapper extends EntityMapper<PhaseOtDTO, PhaseOt> {}
