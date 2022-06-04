package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BondingTrainerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BondingTrainerDTO.class);
        BondingTrainerDTO bondingTrainerDTO1 = new BondingTrainerDTO();
        bondingTrainerDTO1.setId(1L);
        BondingTrainerDTO bondingTrainerDTO2 = new BondingTrainerDTO();
        assertThat(bondingTrainerDTO1).isNotEqualTo(bondingTrainerDTO2);
        bondingTrainerDTO2.setId(bondingTrainerDTO1.getId());
        assertThat(bondingTrainerDTO1).isEqualTo(bondingTrainerDTO2);
        bondingTrainerDTO2.setId(2L);
        assertThat(bondingTrainerDTO1).isNotEqualTo(bondingTrainerDTO2);
        bondingTrainerDTO1.setId(null);
        assertThat(bondingTrainerDTO1).isNotEqualTo(bondingTrainerDTO2);
    }
}
