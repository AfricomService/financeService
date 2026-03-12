package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.DepenseDiverse;
import com.gpm.finance.service.dto.DepenseDiverseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepenseDiverse} and its DTO {@link DepenseDiverseDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepenseDiverseMapper extends EntityMapper<DepenseDiverseDTO, DepenseDiverse> {}
