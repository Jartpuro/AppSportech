package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseModuleMapperTest {

    private CourseModuleMapper courseModuleMapper;

    @BeforeEach
    public void setUp() {
        courseModuleMapper = new CourseModuleMapperImpl();
    }
}
