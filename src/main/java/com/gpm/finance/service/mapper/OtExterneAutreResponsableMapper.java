package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.OtExterneAutreResponsable;
import com.gpm.finance.service.dto.OtExterneAutreResponsableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OtExterneAutreResponsable} and its DTO {@link OtExterneAutreResponsableDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OtExterneAutreResponsableMapper extends EntityMapper<OtExterneAutreResponsableDTO, OtExterneAutreResponsable> {}
