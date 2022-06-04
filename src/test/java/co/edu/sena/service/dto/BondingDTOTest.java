package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BondingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BondingDTO.class);
        BondingDTO bondingDTO1 = new BondingDTO();
        bondingDTO1.setId(1L);
        BondingDTO bondingDTO2 = new BondingDTO();
        assertThat(bondingDTO1).isNotEqualTo(bondingDTO2);
        bondingDTO2.setId(bondingDTO1.getId());
        assertThat(bondingDTO1).isEqualTo(bondingDTO2);
        bondingDTO2.setId(2L);
        assertThat(bondingDTO1).isNotEqualTo(bondingDTO2);
        bondingDTO1.setId(null);
        assertThat(bondingDTO1).isNotEqualTo(bondingDTO2);
    }
}
