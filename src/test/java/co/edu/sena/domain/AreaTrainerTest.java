package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaTrainerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaTrainer.class);
        AreaTrainer areaTrainer1 = new AreaTrainer();
        areaTrainer1.setId(1L);
        AreaTrainer areaTrainer2 = new AreaTrainer();
        areaTrainer2.setId(areaTrainer1.getId());
        assertThat(areaTrainer1).isEqualTo(areaTrainer2);
        areaTrainer2.setId(2L);
        assertThat(areaTrainer1).isNotEqualTo(areaTrainer2);
        areaTrainer1.setId(null);
        assertThat(areaTrainer1).isNotEqualTo(areaTrainer2);
    }
}
