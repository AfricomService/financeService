package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactureWOMapperTest {

    private FactureWOMapper factureWOMapper;

    @BeforeEach
    public void setUp() {
        factureWOMapper = new FactureWOMapperImpl();
    }
}
