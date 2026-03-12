package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FraisDeMissionMapperTest {

    private FraisDeMissionMapper fraisDeMissionMapper;

    @BeforeEach
    public void setUp() {
        fraisDeMissionMapper = new FraisDeMissionMapperImpl();
    }
}
