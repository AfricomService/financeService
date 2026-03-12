package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtExterneMapperTest {

    private OtExterneMapper otExterneMapper;

    @BeforeEach
    public void setUp() {
        otExterneMapper = new OtExterneMapperImpl();
    }
}
