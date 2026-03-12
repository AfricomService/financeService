package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.Facture;
import com.gpm.finance.domain.FactureWO;
import com.gpm.finance.service.dto.FactureDTO;
import com.gpm.finance.service.dto.FactureWODTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FactureWO} and its DTO {@link FactureWODTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureWOMapper extends EntityMapper<FactureWODTO, FactureWO> {
    @Mapping(target = "facture", source = "facture", qualifiedByName = "factureNumFacture")
    FactureWODTO toDto(FactureWO s);

    @Named("factureNumFacture")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numFacture", source = "numFacture")
    FactureDTO toDtoFactureNumFacture(Facture facture);
}
