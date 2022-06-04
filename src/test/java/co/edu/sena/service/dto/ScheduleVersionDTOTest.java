package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduleVersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleVersionDTO.class);
        ScheduleVersionDTO scheduleVersionDTO1 = new ScheduleVersionDTO();
        scheduleVersionDTO1.setId(1L);
        ScheduleVersionDTO scheduleVersionDTO2 = new ScheduleVersionDTO();
        assertThat(scheduleVersionDTO1).isNotEqualTo(scheduleVersionDTO2);
        scheduleVersionDTO2.setId(scheduleVersionDTO1.getId());
        assertThat(scheduleVersionDTO1).isEqualTo(scheduleVersionDTO2);
        scheduleVersionDTO2.setId(2L);
        assertThat(scheduleVersionDTO1).isNotEqualTo(scheduleVersionDTO2);
        scheduleVersionDTO1.setId(null);
        assertThat(scheduleVersionDTO1).isNotEqualTo(scheduleVersionDTO2);
    }
}
