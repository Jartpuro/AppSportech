package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogAuditDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogAuditDTO.class);
        LogAuditDTO logAuditDTO1 = new LogAuditDTO();
        logAuditDTO1.setId(1L);
        LogAuditDTO logAuditDTO2 = new LogAuditDTO();
        assertThat(logAuditDTO1).isNotEqualTo(logAuditDTO2);
        logAuditDTO2.setId(logAuditDTO1.getId());
        assertThat(logAuditDTO1).isEqualTo(logAuditDTO2);
        logAuditDTO2.setId(2L);
        assertThat(logAuditDTO1).isNotEqualTo(logAuditDTO2);
        logAuditDTO1.setId(null);
        assertThat(logAuditDTO1).isNotEqualTo(logAuditDTO2);
    }
}
