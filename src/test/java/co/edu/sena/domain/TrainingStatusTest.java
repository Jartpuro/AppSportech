package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingStatus.class);
        TrainingStatus trainingStatus1 = new TrainingStatus();
        trainingStatus1.setId(1L);
        TrainingStatus trainingStatus2 = new TrainingStatus();
        trainingStatus2.setId(trainingStatus1.getId());
        assertThat(trainingStatus1).isEqualTo(trainingStatus2);
        trainingStatus2.setId(2L);
        assertThat(trainingStatus1).isNotEqualTo(trainingStatus2);
        trainingStatus1.setId(null);
        assertThat(trainingStatus1).isNotEqualTo(trainingStatus2);
    }
}
