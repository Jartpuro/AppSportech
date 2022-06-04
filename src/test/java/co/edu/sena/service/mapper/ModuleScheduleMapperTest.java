package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModuleScheduleMapperTest {

    private ModuleScheduleMapper moduleScheduleMapper;

    @BeforeEach
    public void setUp() {
        moduleScheduleMapper = new ModuleScheduleMapperImpl();
    }
}
