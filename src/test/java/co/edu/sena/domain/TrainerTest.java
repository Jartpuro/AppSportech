package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trainer.class);
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(trainer1.getId());
        assertThat(trainer1).isEqualTo(trainer2);
        trainer2.setId(2L);
        assertThat(trainer1).isNotEqualTo(trainer2);
        trainer1.setId(null);
        assertThat(trainer1).isNotEqualTo(trainer2);
    }
}
