package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraineeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trainee.class);
        Trainee trainee1 = new Trainee();
        trainee1.setId(1L);
        Trainee trainee2 = new Trainee();
        trainee2.setId(trainee1.getId());
        assertThat(trainee1).isEqualTo(trainee2);
        trainee2.setId(2L);
        assertThat(trainee1).isNotEqualTo(trainee2);
        trainee1.setId(null);
        assertThat(trainee1).isNotEqualTo(trainee2);
    }
}
