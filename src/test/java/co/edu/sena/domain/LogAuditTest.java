package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogAuditTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogAudit.class);
        LogAudit logAudit1 = new LogAudit();
        logAudit1.setId(1L);
        LogAudit logAudit2 = new LogAudit();
        logAudit2.setId(logAudit1.getId());
        assertThat(logAudit1).isEqualTo(logAudit2);
        logAudit2.setId(2L);
        assertThat(logAudit1).isNotEqualTo(logAudit2);
        logAudit1.setId(null);
        assertThat(logAudit1).isNotEqualTo(logAudit2);
    }
}
