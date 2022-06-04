package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassroomTypeMapperTest {

    private ClassroomTypeMapper classroomTypeMapper;

    @BeforeEach
    public void setUp() {
        classroomTypeMapper = new ClassroomTypeMapperImpl();
    }
}
