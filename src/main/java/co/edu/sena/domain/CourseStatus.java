package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseStatus.
 */
@Entity
@Table(name = "course_status")
public class CourseStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name_course_status", length = 20, nullable = false, unique = true)
    private String nameCourseStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state_course", nullable = false)
    private State stateCourse;

    @OneToMany(mappedBy = "courseStatus")
    @JsonIgnoreProperties(value = { "trainees", "courseModules", "courseStatus", "trainingProgram" }, allowSetters = true)
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCourseStatus() {
        return this.nameCourseStatus;
    }

    public CourseStatus nameCourseStatus(String nameCourseStatus) {
        this.setNameCourseStatus(nameCourseStatus);
        return this;
    }

    public void setNameCourseStatus(String nameCourseStatus) {
        this.nameCourseStatus = nameCourseStatus;
    }

    public State getStateCourse() {
        return this.stateCourse;
    }

    public CourseStatus stateCourse(State stateCourse) {
        this.setStateCourse(stateCourse);
        return this;
    }

    public void setStateCourse(State stateCourse) {
        this.stateCourse = stateCourse;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        if (this.courses != null) {
            this.courses.forEach(i -> i.setCourseStatus(null));
        }
        if (courses != null) {
            courses.forEach(i -> i.setCourseStatus(this));
        }
        this.courses = courses;
    }

    public CourseStatus courses(Set<Course> courses) {
        this.setCourses(courses);
        return this;
    }

    public CourseStatus addCourse(Course course) {
        this.courses.add(course);
        course.setCourseStatus(this);
        return this;
    }

    public CourseStatus removeCourse(Course course) {
        this.courses.remove(course);
        course.setCourseStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseStatus)) {
            return false;
        }
        return id != null && id.equals(((CourseStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseStatus{" +
            "id=" + getId() +
            ", nameCourseStatus='" + getNameCourseStatus() + "'" +
            ", stateCourse='" + getStateCourse() + "'" +
            "}";
    }
}
