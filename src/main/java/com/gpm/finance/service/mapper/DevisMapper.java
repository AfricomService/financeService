package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.Devis;
import com.gpm.finance.domain.Facture;
import com.gpm.finance.service.dto.DevisDTO;
import com.gpm.finance.service.dto.FactureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Devis} and its DTO {@link DevisDTO}.
 */
@Mapper(componentModel = "spring")
public interface DevisMapper extends EntityMapper<DevisDTO, Devis> {
    @Mapping(target = "facture", source = "facture", qualifiedByName = "factureNumFacture")
    DevisDTO toDto(Devis s);

    @Named("factureNumFacture")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numFacture", source = "numFacture")
    FactureDTO toDtoFactureNumFacture(Facture facture);
}
