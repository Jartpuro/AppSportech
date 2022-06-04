package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Campus.
 */
@Entity
@Table(name = "campus")
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "campus_name", length = 50, nullable = false, unique = true)
    private String campusName;

    @NotNull
    @Size(max = 400)
    @Column(name = "campus_address", length = 400, nullable = false)
    private String campusAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "campus_state", nullable = false)
    private State campusState;

    @OneToMany(mappedBy = "campus")
    @JsonIgnoreProperties(value = { "schedules", "classroomType", "campus" }, allowSetters = true)
    private Set<Classroom> classrooms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Campus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampusName() {
        return this.campusName;
    }

    public Campus campusName(String campusName) {
        this.setCampusName(campusName);
        return this;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCampusAddress() {
        return this.campusAddress;
    }

    public Campus campusAddress(String campusAddress) {
        this.setCampusAddress(campusAddress);
        return this;
    }

    public void setCampusAddress(String campusAddress) {
        this.campusAddress = campusAddress;
    }

    public State getCampusState() {
        return this.campusState;
    }

    public Campus campusState(State campusState) {
        this.setCampusState(campusState);
        return this;
    }

    public void setCampusState(State campusState) {
        this.campusState = campusState;
    }

    public Set<Classroom> getClassrooms() {
        return this.classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        if (this.classrooms != null) {
            this.classrooms.forEach(i -> i.setCampus(null));
        }
        if (classrooms != null) {
            classrooms.forEach(i -> i.setCampus(this));
        }
        this.classrooms = classrooms;
    }

    public Campus classrooms(Set<Classroom> classrooms) {
        this.setClassrooms(classrooms);
        return this;
    }

    public Campus addClassroom(Classroom classroom) {
        this.classrooms.add(classroom);
        classroom.setCampus(this);
        return this;
    }

    public Campus removeClassroom(Classroom classroom) {
        this.classrooms.remove(classroom);
        classroom.setCampus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campus)) {
            return false;
        }
        return id != null && id.equals(((Campus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campus{" +
            "id=" + getId() +
            ", campusName='" + getCampusName() + "'" +
            ", campusAddress='" + getCampusAddress() + "'" +
            ", campusState='" + getCampusState() + "'" +
            "}";
    }
}
