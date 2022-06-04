package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.Offer;
import co.edu.sena.domain.enumeration.StateProgram;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TrainingProgram.
 */
@Entity
@Table(name = "training_program")
public class TrainingProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "program_code", length = 50, nullable = false)
    private String programCode;

    @NotNull
    @Size(max = 40)
    @Column(name = "program_version", length = 40, nullable = false)
    private String programVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "program_name", nullable = false)
    private Offer programName;

    @NotNull
    @Size(max = 40)
    @Column(name = "program_initials", length = 40, nullable = false)
    private String programInitials;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "program_state", nullable = false)
    private StateProgram programState;

    @OneToMany(mappedBy = "trainingProgram")
    @JsonIgnoreProperties(value = { "trainees", "courseModules", "courseStatus", "trainingProgram" }, allowSetters = true)
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrainingProgram id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramCode() {
        return this.programCode;
    }

    public TrainingProgram programCode(String programCode) {
        this.setProgramCode(programCode);
        return this;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramVersion() {
        return this.programVersion;
    }

    public TrainingProgram programVersion(String programVersion) {
        this.setProgramVersion(programVersion);
        return this;
    }

    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    public Offer getProgramName() {
        return this.programName;
    }

    public TrainingProgram programName(Offer programName) {
        this.setProgramName(programName);
        return this;
    }

    public void setProgramName(Offer programName) {
        this.programName = programName;
    }

    public String getProgramInitials() {
        return this.programInitials;
    }

    public TrainingProgram programInitials(String programInitials) {
        this.setProgramInitials(programInitials);
        return this;
    }

    public void setProgramInitials(String programInitials) {
        this.programInitials = programInitials;
    }

    public StateProgram getProgramState() {
        return this.programState;
    }

    public TrainingProgram programState(StateProgram programState) {
        this.setProgramState(programState);
        return this;
    }

    public void setProgramState(StateProgram programState) {
        this.programState = programState;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        if (this.courses != null) {
            this.courses.forEach(i -> i.setTrainingProgram(null));
        }
        if (courses != null) {
            courses.forEach(i -> i.setTrainingProgram(this));
        }
        this.courses = courses;
    }

    public TrainingProgram courses(Set<Course> courses) {
        this.setCourses(courses);
        return this;
    }

    public TrainingProgram addCourse(Course course) {
        this.courses.add(course);
        course.setTrainingProgram(this);
        return this;
    }

    public TrainingProgram removeCourse(Course course) {
        this.courses.remove(course);
        course.setTrainingProgram(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingProgram)) {
            return false;
        }
        return id != null && id.equals(((TrainingProgram) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingProgram{" +
            "id=" + getId() +
            ", programCode='" + getProgramCode() + "'" +
            ", programVersion='" + getProgramVersion() + "'" +
            ", programName='" + getProgramName() + "'" +
            ", programInitials='" + getProgramInitials() + "'" +
            ", programState='" + getProgramState() + "'" +
            "}";
    }
}
