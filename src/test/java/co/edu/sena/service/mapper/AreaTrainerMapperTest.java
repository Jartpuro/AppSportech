package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaTrainerMapperTest {

    private AreaTrainerMapper areaTrainerMapper;

    @BeforeEach
    public void setUp() {
        areaTrainerMapper = new AreaTrainerMapperImpl();
    }
}
