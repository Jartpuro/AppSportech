package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingProgramDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingProgramDTO.class);
        TrainingProgramDTO trainingProgramDTO1 = new TrainingProgramDTO();
        trainingProgramDTO1.setId(1L);
        TrainingProgramDTO trainingProgramDTO2 = new TrainingProgramDTO();
        assertThat(trainingProgramDTO1).isNotEqualTo(trainingProgramDTO2);
        trainingProgramDTO2.setId(trainingProgramDTO1.getId());
        assertThat(trainingProgramDTO1).isEqualTo(trainingProgramDTO2);
        trainingProgramDTO2.setId(2L);
        assertThat(trainingProgramDTO1).isNotEqualTo(trainingProgramDTO2);
        trainingProgramDTO1.setId(null);
        assertThat(trainingProgramDTO1).isNotEqualTo(trainingProgramDTO2);
    }
}
