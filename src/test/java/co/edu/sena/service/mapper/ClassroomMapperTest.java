package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassroomMapperTest {

    private ClassroomMapper classroomMapper;

    @BeforeEach
    public void setUp() {
        classroomMapper = new ClassroomMapperImpl();
    }
}
