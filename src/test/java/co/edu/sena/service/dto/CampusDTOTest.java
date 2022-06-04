package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampusDTO.class);
        CampusDTO campusDTO1 = new CampusDTO();
        campusDTO1.setId(1L);
        CampusDTO campusDTO2 = new CampusDTO();
        assertThat(campusDTO1).isNotEqualTo(campusDTO2);
        campusDTO2.setId(campusDTO1.getId());
        assertThat(campusDTO1).isEqualTo(campusDTO2);
        campusDTO2.setId(2L);
        assertThat(campusDTO1).isNotEqualTo(campusDTO2);
        campusDTO1.setId(null);
        assertThat(campusDTO1).isNotEqualTo(campusDTO2);
    }
}
