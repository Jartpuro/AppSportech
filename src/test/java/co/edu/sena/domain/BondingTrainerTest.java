package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BondingTrainerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BondingTrainer.class);
        BondingTrainer bondingTrainer1 = new BondingTrainer();
        bondingTrainer1.setId(1L);
        BondingTrainer bondingTrainer2 = new BondingTrainer();
        bondingTrainer2.setId(bondingTrainer1.getId());
        assertThat(bondingTrainer1).isEqualTo(bondingTrainer2);
        bondingTrainer2.setId(2L);
        assertThat(bondingTrainer1).isNotEqualTo(bondingTrainer2);
        bondingTrainer1.setId(null);
        assertThat(bondingTrainer1).isNotEqualTo(bondingTrainer2);
    }
}
