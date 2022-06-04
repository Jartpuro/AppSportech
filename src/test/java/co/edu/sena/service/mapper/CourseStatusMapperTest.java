package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseStatusMapperTest {

    private CourseStatusMapper courseStatusMapper;

    @BeforeEach
    public void setUp() {
        courseStatusMapper = new CourseStatusMapperImpl();
    }
}
