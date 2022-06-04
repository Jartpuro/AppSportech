package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourseStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseStatus.class);
        CourseStatus courseStatus1 = new CourseStatus();
        courseStatus1.setId(1L);
        CourseStatus courseStatus2 = new CourseStatus();
        courseStatus2.setId(courseStatus1.getId());
        assertThat(courseStatus1).isEqualTo(courseStatus2);
        courseStatus2.setId(2L);
        assertThat(courseStatus1).isNotEqualTo(courseStatus2);
        courseStatus1.setId(null);
        assertThat(courseStatus1).isNotEqualTo(courseStatus2);
    }
}
