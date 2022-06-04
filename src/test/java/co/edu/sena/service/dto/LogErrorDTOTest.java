package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogErrorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogErrorDTO.class);
        LogErrorDTO logErrorDTO1 = new LogErrorDTO();
        logErrorDTO1.setId(1L);
        LogErrorDTO logErrorDTO2 = new LogErrorDTO();
        assertThat(logErrorDTO1).isNotEqualTo(logErrorDTO2);
        logErrorDTO2.setId(logErrorDTO1.getId());
        assertThat(logErrorDTO1).isEqualTo(logErrorDTO2);
        logErrorDTO2.setId(2L);
        assertThat(logErrorDTO1).isNotEqualTo(logErrorDTO2);
        logErrorDTO1.setId(null);
        assertThat(logErrorDTO1).isNotEqualTo(logErrorDTO2);
    }
}
