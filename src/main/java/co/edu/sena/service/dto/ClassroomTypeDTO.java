package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.ClassroomType} entity.
 */
public class ClassroomTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String typeClassroom;

    @NotNull
    @Size(max = 100)
    private String classroomDescription;

    @NotNull
    private State classroomState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeClassroom() {
        return typeClassroom;
    }

    public void setTypeClassroom(String typeClassroom) {
        this.typeClassroom = typeClassroom;
    }

    public String getClassroomDescription() {
        return classroomDescription;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public State getClassroomState() {
        return classroomState;
    }

    public void setClassroomState(State classroomState) {
        this.classroomState = classroomState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassroomTypeDTO)) {
            return false;
        }

        ClassroomTypeDTO classroomTypeDTO = (ClassroomTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classroomTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassroomTypeDTO{" +
            "id=" + getId() +
            ", typeClassroom='" + getTypeClassroom() + "'" +
            ", classroomDescription='" + getClassroomDescription() + "'" +
            ", classroomState='" + getClassroomState() + "'" +
            "}";
    }
}
