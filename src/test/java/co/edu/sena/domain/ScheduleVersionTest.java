package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduleVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleVersion.class);
        ScheduleVersion scheduleVersion1 = new ScheduleVersion();
        scheduleVersion1.setId(1L);
        ScheduleVersion scheduleVersion2 = new ScheduleVersion();
        scheduleVersion2.setId(scheduleVersion1.getId());
        assertThat(scheduleVersion1).isEqualTo(scheduleVersion2);
        scheduleVersion2.setId(2L);
        assertThat(scheduleVersion1).isNotEqualTo(scheduleVersion2);
        scheduleVersion1.setId(null);
        assertThat(scheduleVersion1).isNotEqualTo(scheduleVersion2);
    }
}
