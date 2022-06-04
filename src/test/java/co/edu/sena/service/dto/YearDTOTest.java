package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class YearDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(YearDTO.class);
        YearDTO yearDTO1 = new YearDTO();
        yearDTO1.setId(1L);
        YearDTO yearDTO2 = new YearDTO();
        assertThat(yearDTO1).isNotEqualTo(yearDTO2);
        yearDTO2.setId(yearDTO1.getId());
        assertThat(yearDTO1).isEqualTo(yearDTO2);
        yearDTO2.setId(2L);
        assertThat(yearDTO1).isNotEqualTo(yearDTO2);
        yearDTO1.setId(null);
        assertThat(yearDTO1).isNotEqualTo(yearDTO2);
    }
}
