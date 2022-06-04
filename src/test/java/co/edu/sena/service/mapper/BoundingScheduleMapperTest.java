package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoundingScheduleMapperTest {

    private BoundingScheduleMapper boundingScheduleMapper;

    @BeforeEach
    public void setUp() {
        boundingScheduleMapper = new BoundingScheduleMapperImpl();
    }
}
