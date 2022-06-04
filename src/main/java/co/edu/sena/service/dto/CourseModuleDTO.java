package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.CourseModule} entity.
 */
public class CourseModuleDTO implements Serializable {

    private Long id;

    private CourseDTO course;

    private ModuleDTO module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public ModuleDTO getModule() {
        return module;
    }

    public void setModule(ModuleDTO module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseModuleDTO)) {
            return false;
        }

        CourseModuleDTO courseModuleDTO = (CourseModuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseModuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseModuleDTO{" +
            "id=" + getId() +
            ", course=" + getCourse() +
            ", module=" + getModule() +
            "}";
    }
}
