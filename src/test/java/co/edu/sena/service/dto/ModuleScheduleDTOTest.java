package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModuleScheduleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleScheduleDTO.class);
        ModuleScheduleDTO moduleScheduleDTO1 = new ModuleScheduleDTO();
        moduleScheduleDTO1.setId(1L);
        ModuleScheduleDTO moduleScheduleDTO2 = new ModuleScheduleDTO();
        assertThat(moduleScheduleDTO1).isNotEqualTo(moduleScheduleDTO2);
        moduleScheduleDTO2.setId(moduleScheduleDTO1.getId());
        assertThat(moduleScheduleDTO1).isEqualTo(moduleScheduleDTO2);
        moduleScheduleDTO2.setId(2L);
        assertThat(moduleScheduleDTO1).isNotEqualTo(moduleScheduleDTO2);
        moduleScheduleDTO1.setId(null);
        assertThat(moduleScheduleDTO1).isNotEqualTo(moduleScheduleDTO2);
    }
}
