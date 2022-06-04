package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseStatusDTO.class);
        CourseStatusDTO courseStatusDTO1 = new CourseStatusDTO();
        courseStatusDTO1.setId(1L);
        CourseStatusDTO courseStatusDTO2 = new CourseStatusDTO();
        assertThat(courseStatusDTO1).isNotEqualTo(courseStatusDTO2);
        courseStatusDTO2.setId(courseStatusDTO1.getId());
        assertThat(courseStatusDTO1).isEqualTo(courseStatusDTO2);
        courseStatusDTO2.setId(2L);
        assertThat(courseStatusDTO1).isNotEqualTo(courseStatusDTO2);
        courseStatusDTO1.setId(null);
        assertThat(courseStatusDTO1).isNotEqualTo(courseStatusDTO2);
    }
}
