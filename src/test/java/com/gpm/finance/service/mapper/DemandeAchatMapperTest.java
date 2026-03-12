package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandeAchatMapperTest {

    private DemandeAchatMapper demandeAchatMapper;

    @BeforeEach
    public void setUp() {
        demandeAchatMapper = new DemandeAchatMapperImpl();
    }
}
