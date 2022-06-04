package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Classroom.
 */
@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "classroom_number", length = 50, nullable = false)
    private String classroomNumber;

    @NotNull
    @Size(max = 1000)
    @Column(name = "classroom_description", length = 1000, nullable = false)
    private String classroomDescription;

    @NotNull
    @Size(max = 40)
    @Column(name = "classroom_state", length = 40, nullable = false)
    private String classroomState;

    @OneToMany(mappedBy = "classroom")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classrooms" }, allowSetters = true)
    private ClassroomType classroomType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classrooms" }, allowSetters = true)
    private Campus campus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classroom id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassroomNumber() {
        return this.classroomNumber;
    }

    public Classroom classroomNumber(String classroomNumber) {
        this.setClassroomNumber(classroomNumber);
        return this;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getClassroomDescription() {
        return this.classroomDescription;
    }

    public Classroom classroomDescription(String classroomDescription) {
        this.setClassroomDescription(classroomDescription);
        return this;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public String getClassroomState() {
        return this.classroomState;
    }

    public Classroom classroomState(String classroomState) {
        this.setClassroomState(classroomState);
        return this;
    }

    public void setClassroomState(String classroomState) {
        this.classroomState = classroomState;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setClassroom(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setClassroom(this));
        }
        this.schedules = schedules;
    }

    public Classroom schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Classroom addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setClassroom(this);
        return this;
    }

    public Classroom removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setClassroom(null);
        return this;
    }

    public ClassroomType getClassroomType() {
        return this.classroomType;
    }

    public void setClassroomType(ClassroomType classroomType) {
        this.classroomType = classroomType;
    }

    public Classroom classroomType(ClassroomType classroomType) {
        this.setClassroomType(classroomType);
        return this;
    }

    public Campus getCampus() {
        return this.campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Classroom campus(Campus campus) {
        this.setCampus(campus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classroom)) {
            return false;
        }
        return id != null && id.equals(((Classroom) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classroom{" +
            "id=" + getId() +
            ", classroomNumber='" + getClassroomNumber() + "'" +
            ", classroomDescription='" + getClassroomDescription() + "'" +
            ", classroomState='" + getClassroomState() + "'" +
            "}";
    }
}
