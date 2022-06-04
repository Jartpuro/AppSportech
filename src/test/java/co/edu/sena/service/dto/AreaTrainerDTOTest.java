package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaTrainerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaTrainerDTO.class);
        AreaTrainerDTO areaTrainerDTO1 = new AreaTrainerDTO();
        areaTrainerDTO1.setId(1L);
        AreaTrainerDTO areaTrainerDTO2 = new AreaTrainerDTO();
        assertThat(areaTrainerDTO1).isNotEqualTo(areaTrainerDTO2);
        areaTrainerDTO2.setId(areaTrainerDTO1.getId());
        assertThat(areaTrainerDTO1).isEqualTo(areaTrainerDTO2);
        areaTrainerDTO2.setId(2L);
        assertThat(areaTrainerDTO1).isNotEqualTo(areaTrainerDTO2);
        areaTrainerDTO1.setId(null);
        assertThat(areaTrainerDTO1).isNotEqualTo(areaTrainerDTO2);
    }
}
