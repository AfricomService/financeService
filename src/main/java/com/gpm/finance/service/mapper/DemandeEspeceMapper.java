package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.DemandeEspece;
import com.gpm.finance.service.dto.DemandeEspeceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeEspece} and its DTO {@link DemandeEspeceDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeEspeceMapper extends EntityMapper<DemandeEspeceDTO, DemandeEspece> {}
