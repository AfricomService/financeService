package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.LiaisonModelPhaseOT;
import com.gpm.finance.service.dto.LiaisonModelPhaseOTDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LiaisonModelPhaseOT} and its DTO {@link LiaisonModelPhaseOTDTO}.
 */
@Mapper(componentModel = "spring")
public interface LiaisonModelPhaseOTMapper extends EntityMapper<LiaisonModelPhaseOTDTO, LiaisonModelPhaseOT> {}
