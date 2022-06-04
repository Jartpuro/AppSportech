package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearMapperTest {

    private YearMapper yearMapper;

    @BeforeEach
    public void setUp() {
        yearMapper = new YearMapperImpl();
    }
}
