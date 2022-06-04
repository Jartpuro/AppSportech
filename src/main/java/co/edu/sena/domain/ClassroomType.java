package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClassroomType.
 */
@Entity
@Table(name = "classroom_type")
public class ClassroomType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "type_classroom", length = 50, nullable = false, unique = true)
    private String typeClassroom;

    @NotNull
    @Size(max = 100)
    @Column(name = "classroom_description", length = 100, nullable = false)
    private String classroomDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "classroom_state", nullable = false)
    private State classroomState;

    @OneToMany(mappedBy = "classroomType")
    @JsonIgnoreProperties(value = { "schedules", "classroomType", "campus" }, allowSetters = true)
    private Set<Classroom> classrooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClassroomType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeClassroom() {
        return this.typeClassroom;
    }

    public ClassroomType typeClassroom(String typeClassroom) {
        this.setTypeClassroom(typeClassroom);
        return this;
    }

    public void setTypeClassroom(String typeClassroom) {
        this.typeClassroom = typeClassroom;
    }

    public String getClassroomDescription() {
        return this.classroomDescription;
    }

    public ClassroomType classroomDescription(String classroomDescription) {
        this.setClassroomDescription(classroomDescription);
        return this;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public State getClassroomState() {
        return this.classroomState;
    }

    public ClassroomType classroomState(State classroomState) {
        this.setClassroomState(classroomState);
        return this;
    }

    public void setClassroomState(State classroomState) {
        this.classroomState = classroomState;
    }

    public Set<Classroom> getClassrooms() {
        return this.classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        if (this.classrooms != null) {
            this.classrooms.forEach(i -> i.setClassroomType(null));
        }
        if (classrooms != null) {
            classrooms.forEach(i -> i.setClassroomType(this));
        }
        this.classrooms = classrooms;
    }

    public ClassroomType classrooms(Set<Classroom> classrooms) {
        this.setClassrooms(classrooms);
        return this;
    }

    public ClassroomType addClassroom(Classroom classroom) {
        this.classrooms.add(classroom);
        classroom.setClassroomType(this);
        return this;
    }

    public ClassroomType removeClassroom(Classroom classroom) {
        this.classrooms.remove(classroom);
        classroom.setClassroomType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassroomType)) {
            return false;
        }
        return id != null && id.equals(((ClassroomType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassroomType{" +
            "id=" + getId() +
            ", typeClassroom='" + getTypeClassroom() + "'" +
            ", classroomDescription='" + getClassroomDescription() + "'" +
            ", classroomState='" + getClassroomState() + "'" +
            "}";
    }
}
