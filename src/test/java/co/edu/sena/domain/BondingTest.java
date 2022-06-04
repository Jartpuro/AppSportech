package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BondingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonding.class);
        Bonding bonding1 = new Bonding();
        bonding1.setId(1L);
        Bonding bonding2 = new Bonding();
        bonding2.setId(bonding1.getId());
        assertThat(bonding1).isEqualTo(bonding2);
        bonding2.setId(2L);
        assertThat(bonding1).isNotEqualTo(bonding2);
        bonding1.setId(null);
        assertThat(bonding1).isNotEqualTo(bonding2);
    }
}
