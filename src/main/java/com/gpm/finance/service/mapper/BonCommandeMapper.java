package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.BonCommande;
import com.gpm.finance.service.dto.BonCommandeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonCommande} and its DTO {@link BonCommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonCommandeMapper extends EntityMapper<BonCommandeDTO, BonCommande> {}
