package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingStatusDTO.class);
        TrainingStatusDTO trainingStatusDTO1 = new TrainingStatusDTO();
        trainingStatusDTO1.setId(1L);
        TrainingStatusDTO trainingStatusDTO2 = new TrainingStatusDTO();
        assertThat(trainingStatusDTO1).isNotEqualTo(trainingStatusDTO2);
        trainingStatusDTO2.setId(trainingStatusDTO1.getId());
        assertThat(trainingStatusDTO1).isEqualTo(trainingStatusDTO2);
        trainingStatusDTO2.setId(2L);
        assertThat(trainingStatusDTO1).isNotEqualTo(trainingStatusDTO2);
        trainingStatusDTO1.setId(null);
        assertThat(trainingStatusDTO1).isNotEqualTo(trainingStatusDTO2);
    }
}
