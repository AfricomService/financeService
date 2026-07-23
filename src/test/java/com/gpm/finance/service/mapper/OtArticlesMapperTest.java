package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtArticlesMapperTest {

    private OtArticlesMapper otArticlesMapper;

    @BeforeEach
    public void setUp() {
        otArticlesMapper = new OtArticlesMapperImpl();
    }
}
