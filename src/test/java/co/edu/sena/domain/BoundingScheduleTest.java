package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoundingScheduleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoundingSchedule.class);
        BoundingSchedule boundingSchedule1 = new BoundingSchedule();
        boundingSchedule1.setId(1L);
        BoundingSchedule boundingSchedule2 = new BoundingSchedule();
        boundingSchedule2.setId(boundingSchedule1.getId());
        assertThat(boundingSchedule1).isEqualTo(boundingSchedule2);
        boundingSchedule2.setId(2L);
        assertThat(boundingSchedule1).isNotEqualTo(boundingSchedule2);
        boundingSchedule1.setId(null);
        assertThat(boundingSchedule1).isNotEqualTo(boundingSchedule2);
    }
}
