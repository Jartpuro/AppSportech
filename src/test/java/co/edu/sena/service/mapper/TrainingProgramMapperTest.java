package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingProgramMapperTest {

    private TrainingProgramMapper trainingProgramMapper;

    @BeforeEach
    public void setUp() {
        trainingProgramMapper = new TrainingProgramMapperImpl();
    }
}
