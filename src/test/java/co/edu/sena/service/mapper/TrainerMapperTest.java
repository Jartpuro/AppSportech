package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerMapperTest {

    private TrainerMapper trainerMapper;

    @BeforeEach
    public void setUp() {
        trainerMapper = new TrainerMapperImpl();
    }
}
