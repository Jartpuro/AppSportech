package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modality.class);
        Modality modality1 = new Modality();
        modality1.setId(1L);
        Modality modality2 = new Modality();
        modality2.setId(modality1.getId());
        assertThat(modality1).isEqualTo(modality2);
        modality2.setId(2L);
        assertThat(modality1).isNotEqualTo(modality2);
        modality1.setId(null);
        assertThat(modality1).isNotEqualTo(modality2);
    }
}
