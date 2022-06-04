package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoundingScheduleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoundingScheduleDTO.class);
        BoundingScheduleDTO boundingScheduleDTO1 = new BoundingScheduleDTO();
        boundingScheduleDTO1.setId(1L);
        BoundingScheduleDTO boundingScheduleDTO2 = new BoundingScheduleDTO();
        assertThat(boundingScheduleDTO1).isNotEqualTo(boundingScheduleDTO2);
        boundingScheduleDTO2.setId(boundingScheduleDTO1.getId());
        assertThat(boundingScheduleDTO1).isEqualTo(boundingScheduleDTO2);
        boundingScheduleDTO2.setId(2L);
        assertThat(boundingScheduleDTO1).isNotEqualTo(boundingScheduleDTO2);
        boundingScheduleDTO1.setId(null);
        assertThat(boundingScheduleDTO1).isNotEqualTo(boundingScheduleDTO2);
    }
}
