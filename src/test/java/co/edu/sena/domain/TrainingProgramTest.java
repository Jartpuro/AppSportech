package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingProgram.class);
        TrainingProgram trainingProgram1 = new TrainingProgram();
        trainingProgram1.setId(1L);
        TrainingProgram trainingProgram2 = new TrainingProgram();
        trainingProgram2.setId(trainingProgram1.getId());
        assertThat(trainingProgram1).isEqualTo(trainingProgram2);
        trainingProgram2.setId(2L);
        assertThat(trainingProgram1).isNotEqualTo(trainingProgram2);
        trainingProgram1.setId(null);
        assertThat(trainingProgram1).isNotEqualTo(trainingProgram2);
    }
}
