package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraineeDTO.class);
        TraineeDTO traineeDTO1 = new TraineeDTO();
        traineeDTO1.setId(1L);
        TraineeDTO traineeDTO2 = new TraineeDTO();
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
        traineeDTO2.setId(traineeDTO1.getId());
        assertThat(traineeDTO1).isEqualTo(traineeDTO2);
        traineeDTO2.setId(2L);
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
        traineeDTO1.setId(null);
        assertThat(traineeDTO1).isNotEqualTo(traineeDTO2);
    }
}
