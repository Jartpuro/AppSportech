package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassroomTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassroomType.class);
        ClassroomType classroomType1 = new ClassroomType();
        classroomType1.setId(1L);
        ClassroomType classroomType2 = new ClassroomType();
        classroomType2.setId(classroomType1.getId());
        assertThat(classroomType1).isEqualTo(classroomType2);
        classroomType2.setId(2L);
        assertThat(classroomType1).isNotEqualTo(classroomType2);
        classroomType1.setId(null);
        assertThat(classroomType1).isNotEqualTo(classroomType2);
    }
}
