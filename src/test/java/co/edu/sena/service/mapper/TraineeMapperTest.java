package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeMapperTest {

    private TraineeMapper traineeMapper;

    @BeforeEach
    public void setUp() {
        traineeMapper = new TraineeMapperImpl();
    }
}
