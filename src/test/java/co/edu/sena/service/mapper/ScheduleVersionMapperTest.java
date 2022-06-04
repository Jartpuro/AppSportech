package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScheduleVersionMapperTest {

    private ScheduleVersionMapper scheduleVersionMapper;

    @BeforeEach
    public void setUp() {
        scheduleVersionMapper = new ScheduleVersionMapperImpl();
    }
}
