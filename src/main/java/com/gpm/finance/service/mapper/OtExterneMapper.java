package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.OtExterne;
import com.gpm.finance.service.dto.OtExterneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OtExterne} and its DTO {@link OtExterneDTO}.
 */
@Mapper(componentModel = "spring")
public interface OtExterneMapper extends EntityMapper<OtExterneDTO, OtExterne> {}
