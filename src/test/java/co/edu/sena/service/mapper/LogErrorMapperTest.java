package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogErrorMapperTest {

    private LogErrorMapper logErrorMapper;

    @BeforeEach
    public void setUp() {
        logErrorMapper = new LogErrorMapperImpl();
    }
}
