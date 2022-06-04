package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModalityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalityDTO.class);
        ModalityDTO modalityDTO1 = new ModalityDTO();
        modalityDTO1.setId(1L);
        ModalityDTO modalityDTO2 = new ModalityDTO();
        assertThat(modalityDTO1).isNotEqualTo(modalityDTO2);
        modalityDTO2.setId(modalityDTO1.getId());
        assertThat(modalityDTO1).isEqualTo(modalityDTO2);
        modalityDTO2.setId(2L);
        assertThat(modalityDTO1).isNotEqualTo(modalityDTO2);
        modalityDTO1.setId(null);
        assertThat(modalityDTO1).isNotEqualTo(modalityDTO2);
    }
}
