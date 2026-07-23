package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.BonCommandeArticles;
import com.gpm.finance.service.dto.BonCommandeArticlesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonCommandeArticles} and its DTO {@link BonCommandeArticlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface BonCommandeArticlesMapper extends EntityMapper<BonCommandeArticlesDTO, BonCommandeArticles> {}
