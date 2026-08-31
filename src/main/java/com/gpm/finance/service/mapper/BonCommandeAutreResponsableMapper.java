package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.BonCommandeAutreResponsable;
import com.gpm.finance.service.dto.BonCommandeAutreResponsableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonCommandeAutreResponsable} and its DTO {@link BonCommandeAutreResponsableDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BonCommandeAutreResponsableMapper extends EntityMapper<BonCommandeAutreResponsableDTO, BonCommandeAutreResponsable> {}
