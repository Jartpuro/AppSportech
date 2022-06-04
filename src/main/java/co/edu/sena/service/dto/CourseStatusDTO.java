package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.CourseStatus} entity.
 */
public class CourseStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String nameCourseStatus;

    @NotNull
    private State stateCourse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCourseStatus() {
        return nameCourseStatus;
    }

    public void setNameCourseStatus(String nameCourseStatus) {
        this.nameCourseStatus = nameCourseStatus;
    }

    public State getStateCourse() {
        return stateCourse;
    }

    public void setStateCourse(State stateCourse) {
        this.stateCourse = stateCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseStatusDTO)) {
            return false;
        }

        CourseStatusDTO courseStatusDTO = (CourseStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseStatusDTO{" +
            "id=" + getId() +
            ", nameCourseStatus='" + getNameCourseStatus() + "'" +
            ", stateCourse='" + getStateCourse() + "'" +
            "}";
    }
}
