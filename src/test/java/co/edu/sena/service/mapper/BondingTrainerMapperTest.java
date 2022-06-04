package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BondingTrainerMapperTest {

    private BondingTrainerMapper bondingTrainerMapper;

    @BeforeEach
    public void setUp() {
        bondingTrainerMapper = new BondingTrainerMapperImpl();
    }
}
