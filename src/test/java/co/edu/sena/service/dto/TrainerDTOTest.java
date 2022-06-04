package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainerDTO.class);
        TrainerDTO trainerDTO1 = new TrainerDTO();
        trainerDTO1.setId(1L);
        TrainerDTO trainerDTO2 = new TrainerDTO();
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
        trainerDTO2.setId(trainerDTO1.getId());
        assertThat(trainerDTO1).isEqualTo(trainerDTO2);
        trainerDTO2.setId(2L);
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
        trainerDTO1.setId(null);
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
    }
}
