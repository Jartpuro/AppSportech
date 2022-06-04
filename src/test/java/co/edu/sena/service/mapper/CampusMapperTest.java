package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampusMapperTest {

    private CampusMapper campusMapper;

    @BeforeEach
    public void setUp() {
        campusMapper = new CampusMapperImpl();
    }
}
