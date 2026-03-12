package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepenseDiverseMapperTest {

    private DepenseDiverseMapper depenseDiverseMapper;

    @BeforeEach
    public void setUp() {
        depenseDiverseMapper = new DepenseDiverseMapperImpl();
    }
}
