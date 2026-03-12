package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleDemandeAchatMapperTest {

    private ArticleDemandeAchatMapper articleDemandeAchatMapper;

    @BeforeEach
    public void setUp() {
        articleDemandeAchatMapper = new ArticleDemandeAchatMapperImpl();
    }
}
