package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassroomTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassroomTypeDTO.class);
        ClassroomTypeDTO classroomTypeDTO1 = new ClassroomTypeDTO();
        classroomTypeDTO1.setId(1L);
        ClassroomTypeDTO classroomTypeDTO2 = new ClassroomTypeDTO();
        assertThat(classroomTypeDTO1).isNotEqualTo(classroomTypeDTO2);
        classroomTypeDTO2.setId(classroomTypeDTO1.getId());
        assertThat(classroomTypeDTO1).isEqualTo(classroomTypeDTO2);
        classroomTypeDTO2.setId(2L);
        assertThat(classroomTypeDTO1).isNotEqualTo(classroomTypeDTO2);
        classroomTypeDTO1.setId(null);
        assertThat(classroomTypeDTO1).isNotEqualTo(classroomTypeDTO2);
    }
}
