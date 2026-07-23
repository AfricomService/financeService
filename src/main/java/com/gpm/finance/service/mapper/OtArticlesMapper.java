package com.gpm.finance.service.mapper;

import com.gpm.finance.domain.OtArticles;
import com.gpm.finance.service.dto.OtArticlesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OtArticles} and its DTO {@link OtArticlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface OtArticlesMapper extends EntityMapper<OtArticlesDTO, OtArticles> {}
