package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.DemandeAchat;
import com.gpm.finance.service.dto.DemandeAchatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandeAchat} and its DTO {@link DemandeAchatDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandeAchatMapper extends EntityMapper<DemandeAchatDTO, DemandeAchat> {}
