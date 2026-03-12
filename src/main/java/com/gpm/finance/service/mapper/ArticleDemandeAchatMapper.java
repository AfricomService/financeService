package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.ArticleDemandeAchat;
import com.gpm.finance.domain.DemandeAchat;
import com.gpm.finance.service.dto.ArticleDemandeAchatDTO;
import com.gpm.finance.service.dto.DemandeAchatDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleDemandeAchat} and its DTO {@link ArticleDemandeAchatDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleDemandeAchatMapper extends EntityMapper<ArticleDemandeAchatDTO, ArticleDemandeAchat> {
    @Mapping(target = "demandeAchat", source = "demandeAchat", qualifiedByName = "demandeAchatCode")
    ArticleDemandeAchatDTO toDto(ArticleDemandeAchat s);

    @Named("demandeAchatCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    DemandeAchatDTO toDtoDemandeAchatCode(DemandeAchat demandeAchat);
}
