package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModuleScheduleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleSchedule.class);
        ModuleSchedule moduleSchedule1 = new ModuleSchedule();
        moduleSchedule1.setId(1L);
        ModuleSchedule moduleSchedule2 = new ModuleSchedule();
        moduleSchedule2.setId(moduleSchedule1.getId());
        assertThat(moduleSchedule1).isEqualTo(moduleSchedule2);
        moduleSchedule2.setId(2L);
        assertThat(moduleSchedule1).isNotEqualTo(moduleSchedule2);
        moduleSchedule1.setId(null);
        assertThat(moduleSchedule1).isNotEqualTo(moduleSchedule2);
    }
}
