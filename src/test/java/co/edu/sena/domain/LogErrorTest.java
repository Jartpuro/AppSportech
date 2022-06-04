package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LogErrorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogError.class);
        LogError logError1 = new LogError();
        logError1.setId(1L);
        LogError logError2 = new LogError();
        logError2.setId(logError1.getId());
        assertThat(logError1).isEqualTo(logError2);
        logError2.setId(2L);
        assertThat(logError1).isNotEqualTo(logError2);
        logError1.setId(null);
        assertThat(logError1).isNotEqualTo(logError2);
    }
}
