package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingStatusMapperTest {

    private TrainingStatusMapper trainingStatusMapper;

    @BeforeEach
    public void setUp() {
        trainingStatusMapper = new TrainingStatusMapperImpl();
    }
}
