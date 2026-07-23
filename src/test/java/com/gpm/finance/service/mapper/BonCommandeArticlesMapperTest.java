package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BonCommandeArticlesMapperTest {

    private BonCommandeArticlesMapper bonCommandeArticlesMapper;

    @BeforeEach
    public void setUp() {
        bonCommandeArticlesMapper = new BonCommandeArticlesMapperImpl();
    }
}
