package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandeEspeceMapperTest {

    private DemandeEspeceMapper demandeEspeceMapper;

    @BeforeEach
    public void setUp() {
        demandeEspeceMapper = new DemandeEspeceMapperImpl();
    }
}
