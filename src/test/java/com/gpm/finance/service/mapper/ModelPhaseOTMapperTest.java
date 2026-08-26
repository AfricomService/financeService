package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelPhaseOTMapperTest {

    private ModelPhaseOTMapper modelPhaseOTMapper;

    @BeforeEach
    public void setUp() {
        modelPhaseOTMapper = new ModelPhaseOTMapperImpl();
    }
}
