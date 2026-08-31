package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BonCommandeAutreResponsableMapperTest {

    private BonCommandeAutreResponsableMapper bonCommandeAutreResponsableMapper;

    @BeforeEach
    public void setUp() {
        bonCommandeAutreResponsableMapper = new BonCommandeAutreResponsableMapperImpl();
    }
}
