package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseModuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseModuleDTO.class);
        CourseModuleDTO courseModuleDTO1 = new CourseModuleDTO();
        courseModuleDTO1.setId(1L);
        CourseModuleDTO courseModuleDTO2 = new CourseModuleDTO();
        assertThat(courseModuleDTO1).isNotEqualTo(courseModuleDTO2);
        courseModuleDTO2.setId(courseModuleDTO1.getId());
        assertThat(courseModuleDTO1).isEqualTo(courseModuleDTO2);
        courseModuleDTO2.setId(2L);
        assertThat(courseModuleDTO1).isNotEqualTo(courseModuleDTO2);
        courseModuleDTO1.setId(null);
        assertThat(courseModuleDTO1).isNotEqualTo(courseModuleDTO2);
    }
}
