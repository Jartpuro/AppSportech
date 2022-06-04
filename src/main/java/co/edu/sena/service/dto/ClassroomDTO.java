package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Classroom} entity.
 */
public class ClassroomDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String classroomNumber;

    @NotNull
    @Size(max = 1000)
    private String classroomDescription;

    @NotNull
    @Size(max = 40)
    private String classroomState;

    private ClassroomTypeDTO classroomType;

    private CampusDTO campus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getClassroomDescription() {
        return classroomDescription;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public String getClassroomState() {
        return classroomState;
    }

    public void setClassroomState(String classroomState) {
        this.classroomState = classroomState;
    }

    public ClassroomTypeDTO getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(ClassroomTypeDTO classroomType) {
        this.classroomType = classroomType;
    }

    public CampusDTO getCampus() {
        return campus;
    }

    public void setCampus(CampusDTO campus) {
        this.campus = campus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassroomDTO)) {
            return false;
        }

        ClassroomDTO classroomDTO = (ClassroomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classroomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassroomDTO{" +
            "id=" + getId() +
            ", classroomNumber='" + getClassroomNumber() + "'" +
            ", classroomDescription='" + getClassroomDescription() + "'" +
            ", classroomState='" + getClassroomState() + "'" +
            ", classroomType=" + getClassroomType() +
            ", campus=" + getCampus() +
            "}";
    }
}
